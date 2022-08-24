package dev.t1dmlgus.ordermvp2.order.application;

import dev.t1dmlgus.ordermvp2.order.domain.DeliveryInfo;
import lombok.Getter;
import java.util.List;

public class OrderCommand {

    @Getter
    public static class placeOrder{

        private final List<OrderProduct> orderProducts;
        private final String memberToken;
        private final DeliveryInfo deliveryInfo;

        public placeOrder(List<OrderProduct> orderProducts, String memberToken, DeliveryInfo deliveryInfo) {
            this.orderProducts = orderProducts;
            this.memberToken = memberToken;
            this.deliveryInfo = deliveryInfo;
        }
    }


    @Getter
    static class OrderProduct {

        private final String productToken;
        private final int quantity;

        public OrderProduct(String productToken, int quantity) {
            this.productToken = productToken;
            this.quantity = quantity;
        }
    }



}
