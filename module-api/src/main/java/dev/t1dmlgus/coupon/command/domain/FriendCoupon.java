package dev.t1dmlgus.coupon.command.domain;


import dev.t1dmlgus.common.error.exception.InvalidException;
import dev.t1dmlgus.common.error.type.ErrorType;
import dev.t1dmlgus.common.util.money.Money;
import dev.t1dmlgus.order.command.domain.Order;

public class FriendCoupon extends Coupon {

    @Override
    public void calculateCoupon(Order order) {
        if (order.getTotalAmounts().getValue() < 20_000) {
            throw new InvalidException(ErrorType.CANNOT_USE_COUPON);
        }
        order.discountTotalAmount(new Money(5_000));
    }
}
