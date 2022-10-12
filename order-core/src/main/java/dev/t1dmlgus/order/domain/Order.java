package dev.t1dmlgus.order.domain;


import dev.t1dmlgus.common.error.ErrorType;
import dev.t1dmlgus.common.error.exception.InvalidException;
import dev.t1dmlgus.common.error.exception.NotFoundException;
import dev.t1dmlgus.common.util.AbstractEntity;
import dev.t1dmlgus.common.util.Money;
import dev.t1dmlgus.common.util.MoneyConverter;
import dev.t1dmlgus.common.util.TokenUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Table(name = "orders")
@Entity
public class Order extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderToken;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name="order_id"))
    private List<OrderLine> orderLines;

    private String memberToken;

    @Convert(converter = MoneyConverter.class)
    private Money totalAmounts;

    @Embedded
    private DeliveryInfo deliveryInfo;

    @Enumerated(EnumType.STRING)
    private OrderState orderState;


    @RequiredArgsConstructor
    @Getter
    public enum OrderState{

        PAYMENT_WAITING("결제 전"),
        PAYMENT_COMPLETE("결제완료/상품 준비중"),
        SHIPPED("출고"),
        DELIVERING("배송중"),
        DELIVERY_COMPLETE("배송완료"),
        CANCEL("주문취소");

        private final String description;
    }

    @Builder
    private Order(String memberToken, DeliveryInfo deliveryInfo) {
        //setDelivery(deliveryInfo);
        setMemberToken(memberToken);
        this.orderState = OrderState.PAYMENT_WAITING;
        this.orderToken = TokenUtil.generateToken("order");
    }

    public static Order newInstance(String memberToken){
        return Order.builder()
                .memberToken(memberToken)
                .build();
    }

    private void setMemberToken(String memberToken) {
        if (memberToken.equals("")) {
            throw new InvalidException(ErrorType.INVALID_PARAMETER_MEMBER_TOKEN);
        }
        this.memberToken = memberToken;
    }


    private void setDelivery(DeliveryInfo deliveryInfo) {
        if(deliveryInfo == null){
            throw new InvalidException(ErrorType.INVALID_PARAMETER_DELIVERY);
        }
        this.deliveryInfo = deliveryInfo;
    }


    public void setOrderLines(List<OrderLine> orderLines) {
        verifyOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void verifyOrderLines(List<OrderLine> orderLines) {
        if(orderLines == null || orderLines.isEmpty())
            throw new NotFoundException(ErrorType.NOT_FOUND_ORDER);
    }


    public void calculateTotalAmounts(){
        double sum = orderLines.stream()
                .mapToDouble(i -> i.getAmounts().getValue())
                .sum();
        this.totalAmounts = new Money(sum);
    }

    public void discountTotalAmount(Money discountMoney) {
        this.totalAmounts.minus(discountMoney);
    }


    public void changeDeliveryInfo(DeliveryInfo newDeliveryInfo){
        verifyNotYetShipped();
        this.deliveryInfo = newDeliveryInfo;
    }

    private void verifyNotYetShipped() {
        if(orderState != OrderState.PAYMENT_COMPLETE && orderState != OrderState.PAYMENT_WAITING){
            throw new InvalidException(ErrorType.INVALID_CHANGE_DELIVERY);
        }
    }

    private void cancel(){
        verifyNotYetShipped();
        orderState = OrderState.CANCEL;
    }

    public void paymentComplete(){
        this.orderState = OrderState.PAYMENT_COMPLETE;
    }


}
