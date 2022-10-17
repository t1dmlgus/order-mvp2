package dev.t1dmlgus.coupon.command.domain;

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
            return new ItCategoryCoupon();
        }
    },
    EMPLOYMENT_COUPON {
        @Override
        public Coupon create() {
            return new JobCategoryCoupon();
        }
    };

    public abstract Coupon create();
}