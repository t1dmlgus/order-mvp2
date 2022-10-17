package dev.t1dmlgus.coupon.command.domain;

import dev.t1dmlgus.common.util.money.Money;
import dev.t1dmlgus.order.command.domain.Order;


public class FirstOrderCoupon extends Coupon {

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
