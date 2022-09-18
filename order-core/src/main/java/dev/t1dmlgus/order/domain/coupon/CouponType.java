package dev.t1dmlgus.order.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponType {

    FRIEND_COUPON {
        @Override
        public Coupon create() {
            return new FriendCoupon();
        }
    },
    FIRST_ORDER{
        public Coupon create(){
            return new FirstOrderCoupon();
        }
    },

    IT_COUPON {
        @Override
        public Coupon create() {
            return null;
        }
    },
    EMPLOYMENT_COUPON {
        @Override
        public Coupon create() {
            return null;
        }
    };

    public abstract Coupon create();
}