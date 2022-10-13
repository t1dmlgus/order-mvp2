package dev.t1dmlgus.order.application;


import dev.t1dmlgus.common.error.ErrorType;
import dev.t1dmlgus.common.error.exception.NotFoundException;
import dev.t1dmlgus.coupon.Coupon;
import dev.t1dmlgus.coupon.CouponType;
import dev.t1dmlgus.order.domain.Order;
import dev.t1dmlgus.order.domain.OrderRepository;
import dev.t1dmlgus.order.infrastructure.OrderLineFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderLineFactory orderLineFactory;
    private final OrderRepository orderRepository;


    @Transactional
    public String placeOrder(OrderCommand.PlaceOrder placeOrder){

        Order order = Order.newInstance(placeOrder.getMemberToken());
        order.setOrderLines(orderLineFactory.store(placeOrder.getOrderProducts()));
        Order save = orderRepository.save(order);
        return save.getOrderToken();
    }


    @Transactional(readOnly = true)
    public String viewOrder(String orderToken){
        Order order = existOrder(orderToken);
        return order.getOrderToken();
    }

    @Transactional
    public void payOrder(String orderToken, String coupon_type){

        // 주문
        Order order = existOrder(orderToken);
        // 쿠폰 적용
        applyCoupon(coupon_type, order);
        // 결제 생성
    }


    private Order existOrder(String orderToken) {
        return orderRepository.findByOrderToken(orderToken)
                .orElseThrow(()->new NotFoundException(ErrorType.NOT_FOUND_ORDER));
    }

    private static void applyCoupon(String couponType, Order order) {
        Coupon coupon = CouponType.valueOf(couponType).create();
        // 추후 유효성 검증(쿠폰)
//        coupon.validation();
        // 쿠폰 적용(금액 할인)
        coupon.calculateCoupon(order);
    }

}