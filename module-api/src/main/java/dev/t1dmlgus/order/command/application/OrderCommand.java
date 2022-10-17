package dev.t1dmlgus.order.command.application;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class OrderCommand {

    @ToString
    @Getter
    public static class PlaceOrder {

        private final List<OrderProduct> orderProducts;
        private final String memberToken;
        //private final OrderDeliveryInfo orderDeliveryInfo;

        @Builder
        private PlaceOrder(List<OrderProduct> orderProducts, String memberToken) {
            this.orderProducts = orderProducts;
            this.memberToken = memberToken;
        }

        public static PlaceOrder newInstance(List<OrderProduct> orderProducts, String memberToken){
            return PlaceOrder.builder()
                    .orderProducts(orderProducts)
                    .memberToken(memberToken)
                    .build();
        }

    }

    @Getter
    public static class OrderProduct {

        private final String productToken;
        private final int quantity;

        @Builder
        private OrderProduct(String productToken, int quantity) {
            this.productToken = productToken;
            this.quantity = quantity;
        }

        public static OrderProduct newInstance(String productToken, int quantity){
            return OrderProduct.builder()
                    .productToken(productToken)
                    .quantity(quantity)
                    .build();
        }
    }

    @Getter
    public static class OrderDeliveryInfo {

        private final String receiverName;
        private final String receiverPhoneNum;
        private final String zipCode;
        private final String address1;
        private final String address2;
        private final String msg;


        @Builder
        private OrderDeliveryInfo(String receiverName, String receiverPhoneNum,
                                 String zipCode, String address1, String address2, String msg) {
            this.receiverName = receiverName;
            this.receiverPhoneNum = receiverPhoneNum;
            this.zipCode = zipCode;
            this.address1 = address1;
            this.address2 = address2;
            this.msg = msg;
        }

        public static OrderDeliveryInfo newInstance(String receiverName, String receiverPhoneNum,
                                                    String zipCode, String address1, String address2, String msg){
            return OrderDeliveryInfo.builder()
                    .receiverName(receiverName)
                    .receiverPhoneNum(receiverPhoneNum)
                    .zipCode(zipCode)
                    .address1(address1)
                    .address2(address2)
                    .msg(msg)
                    .build();
        }
    }


    @Getter
    public static class PayOrder{

        private String orderToken;
        private String coupon;

    }






}
