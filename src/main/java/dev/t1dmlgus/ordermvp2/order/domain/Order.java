package dev.t1dmlgus.ordermvp2.order.domain;


import dev.t1dmlgus.ordermvp2.AbstractEntity;
import dev.t1dmlgus.ordermvp2.common.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Getter
@Table(name = "orders")
@Entity
public class Order extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_line", joinColumns = @JoinColumn(name="order_id"))
    @OrderColumn(name = "order_line_id")
    private List<OrderLine> orderLines;

    private Long ordererId;

    private Money totalAmounts;

    @Embedded
    private DeliveryInfo deliveryInfo;

    @Enumerated
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

    public Order(List<OrderLine> orderLines, Long userId, DeliveryInfo deliveryInfo, OrderState orderState) {
        setOrderLines(orderLines);
        setDelivery(deliveryInfo);
        setUserId(userId);
        this.orderState = OrderState.PAYMENT_WAITING;
    }



    private void setUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("주문자는 필수입니다.");
        }
        this.ordererId = userId;
    }


    private void setDelivery(DeliveryInfo deliveryInfo) {
        if(deliveryInfo == null){
            throw new IllegalArgumentException("배송 정보는 필수입니다.");
        }
        this.deliveryInfo = deliveryInfo;
    }


    private void setOrderLines(List<OrderLine> orderLines) {
        verifyOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void verifyOrderLines(List<OrderLine> orderLines) {
        if(orderLines == null || orderLines.isEmpty())
            throw new IllegalArgumentException("주문 상품이 없습니다.");
    }


    public void calculateTotalAmounts(){
        int sum = orderLines.stream()
                .mapToInt(i -> i.getAmounts().getValue())
                .sum();
        this.totalAmounts = new Money(sum);
    }


    public void changeDeliveryInfo(DeliveryInfo newDeliveryInfo){
        verifyNotYetShipped();
        this.deliveryInfo = newDeliveryInfo;
    }

    private void verifyNotYetShipped() {
        if(orderState != OrderState.PAYMENT_COMPLETE && orderState != OrderState.PAYMENT_WAITING){
            throw new IllegalStateException("배송지 변경을 할수 없습니다."); // 추후 예외처리
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
