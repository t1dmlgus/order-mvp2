package dev.t1dmlgus.coupon.command.domain;

import dev.t1dmlgus.common.util.money.Money;
import dev.t1dmlgus.order.command.domain.Order;

public class JobCategoryCoupon extends Coupon {
    @Override
    public void calculateCoupon(Order order) {

        Money discountMoney = order.getTotalAmounts().multiply( 0.07);
        if (discountMoney.getValue() > 50_000) {
            discountMoney = new Money(50_000);
        }
        order.discountTotalAmount(discountMoney);
    }
}
