package dev.t1dmlgus.order.domain.coupon;


import dev.t1dmlgus.common.util.Money;
import dev.t1dmlgus.order.domain.Order;

public class ItCategoryCoupon extends Coupon{
    @Override
    public void calculateCoupon(Order order) {

        Money discountMoney = order.getTotalAmounts().multiply( 0.05);
        if (discountMoney.getValue() > 100_000) {
            discountMoney = new Money(100_000);
        }
        order.discountTotalAmount(discountMoney);
    }
}
