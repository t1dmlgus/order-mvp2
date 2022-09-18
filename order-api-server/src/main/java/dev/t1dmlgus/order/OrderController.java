package dev.t1dmlgus.order;


import dev.t1dmlgus.order.application.OrderCommand;
import dev.t1dmlgus.order.application.OrderService;
import dev.t1dmlgus.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/order")
    public ResponseEntity<CommonResponse<String>> placeOrder(@RequestBody OrderDto.PlaceOrder placeOrderDto) {

        OrderCommand.PlaceOrder placeOrder = placeOrderDto.toCommandOrder();
        String orderToken = orderService.placeOrder(placeOrder);
        CommonResponse<String> commonResponse = CommonResponse.of(orderToken, "주문이 완료되었습니다.");
        return ResponseEntity.ok(commonResponse);
    }
}