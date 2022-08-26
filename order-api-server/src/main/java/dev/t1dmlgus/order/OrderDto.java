package dev.t1dmlgus.order;

import dev.t1dmlgus.order.application.OrderCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;


public class OrderDto {

    @NoArgsConstructor
    @ToString
    @Getter
    public static class PlaceOrder {

        @Valid
        private List<OrderProduct> orderProducts;
        @NotBlank(message = "유저 번호는 필수 값입니다.")
        private String memberToken;
        @Valid
        private DeliveryInfo deliveryInfo;

        public PlaceOrder(List<OrderProduct> orderProducts, String memberToken, DeliveryInfo deliveryInfo) {
            this.orderProducts = orderProducts;
            this.memberToken = memberToken;
            this.deliveryInfo = deliveryInfo;
        }

        public OrderCommand.PlaceOrder toCommandOrder() {
            List<OrderCommand.OrderProduct> commandOrderProduct =
                    orderProducts.stream().map(OrderProduct::toCommandOrderProduct).collect(Collectors.toList());
            OrderCommand.OrderDeliveryInfo orderDeliveryInfo = deliveryInfo.toCommandDeliveryInfo();

            return OrderCommand.PlaceOrder.newInstance(commandOrderProduct, memberToken, orderDeliveryInfo);
        }
    }

    @NoArgsConstructor
    @ToString
    @Getter
    public static class OrderProduct {

        @NotBlank(message = "상품 번호는 필수값입니다.")
        private String productToken;
        @NotBlank(message = "상품갯수를 입력해주세요")
        private int quantity;

        public OrderProduct(String productToken, int quantity) {
            this.productToken = productToken;
            this.quantity = quantity;
        }

        public OrderCommand.OrderProduct toCommandOrderProduct(){
            return OrderCommand.OrderProduct.newInstance(productToken, quantity);
        }
    }

    @NoArgsConstructor
    @ToString
    @Getter
    public static class DeliveryInfo {

        @NotBlank(message = "받는 분의 이름은 필수 입니다.")
        private String receiverName;
        @NotBlank(message = "받는 분의 핸드폰 번호는 필수 입니다.")
        private String receiverPhoneNum;
        @NotBlank(message = "우편번호는 필수 입니다.")
        private String zipCode;
        @NotBlank(message = "배송 주소1은 필수 입니다.")
        private String address1;
        @NotBlank(message = "배송 주소2는 필수입니다.")
        private String address2;
        @NotBlank(message = "배송 시 메시지를 입력해주세요")
        private String msg;

        public DeliveryInfo(String receiverName, String receiverPhoneNum, String zipCode, String address1, String address2, String msg) {
            this.receiverName = receiverName;
            this.receiverPhoneNum = receiverPhoneNum;
            this.zipCode = zipCode;
            this.address1 = address1;
            this.address2 = address2;
            this.msg = msg;
        }

        public OrderCommand.OrderDeliveryInfo toCommandDeliveryInfo(){
            return OrderCommand.OrderDeliveryInfo.newInstance(
                    receiverName, receiverPhoneNum, zipCode, address1, address2, msg);
        }
    }
}
