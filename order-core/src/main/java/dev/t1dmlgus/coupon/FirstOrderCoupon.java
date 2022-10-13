package dev.t1dmlgus.coupon;

import dev.t1dmlgus.common.util.Money;
import dev.t1dmlgus.order.domain.Order;


public class FirstOrderCoupon extends Coupon{

    /**
     * 쿠폰 적용(첫 거래 할인)
     *
     * @param order completed order
     */

    @Override
    public void calculateCoupon(Order order) {

        Money discountMoney = order.getTotalAmounts().multiply( 0.05);
        if (discountMoney.getValue() > 1_000_000) {
            discountMoney = new Money(1_000_000);
        }
        order.discountTotalAmount(discountMoney);
    }

}
