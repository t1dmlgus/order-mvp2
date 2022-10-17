package dev.t1dmlgus.order.ui;


import dev.t1dmlgus.common.util.response.CommonResponse;
import dev.t1dmlgus.order.command.application.OrderCommand;
import dev.t1dmlgus.order.command.application.OrderDto;
import dev.t1dmlgus.order.command.application.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문
     *
     * @param placeOrderDto DTO to placeOrder
     * @param request HttpServletRequest to Session
     * @return orderToken, message(success)
     */
    @PostMapping("/api/v1/order")
    public ResponseEntity<CommonResponse<String>> placeOrder(
            @RequestBody OrderDto.PlaceOrder placeOrderDto, HttpServletRequest request) {

        String memberToken = getSession(request);
        placeOrderDto.setMemberToken(memberToken);

        OrderCommand.PlaceOrder placeOrder = placeOrderDto.toCommandOrder();
        String orderToken = orderService.placeOrder(placeOrder);
        CommonResponse<String> commonResponse = CommonResponse.of(orderToken, "주문이 완료되었습니다.");
        return ResponseEntity.ok(commonResponse);
    }

    /**
     * 세션 가져오기
     *
     * @param request HttpServletRequest to Session
     * @return memberToken
     */
    private static String getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("LOGIN_MEMBER");
    }
}










