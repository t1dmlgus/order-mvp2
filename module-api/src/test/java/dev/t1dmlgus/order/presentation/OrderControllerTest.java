package dev.t1dmlgus.order.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.t1dmlgus.order.command.application.OrderCommand;
import dev.t1dmlgus.order.command.application.OrderService;
import dev.t1dmlgus.order.ui.OrderController;
import dev.t1dmlgus.order.command.application.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = OrderController.class)
class OrderControllerTest {


    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private MockHttpSession mockSession;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        mockSession = new MockHttpSession();
        mockSession.setAttribute("LOGIN_MEMBER", "M12345678");

    }

    @Test
    void placeOrder() throws Exception {

        //given
        String orderToken = "B12345678";
        String memberToken = "M11111111";
        ArrayList<OrderDto.OrderProduct> ar = new ArrayList<>();
            ar.add(new OrderDto.OrderProduct("P1111111", 3));
            ar.add(new OrderDto.OrderProduct("P2222222", 8));
            ar.add(new OrderDto.OrderProduct("P3333333", 12));

        OrderDto.DeliveryInfo deliveryInfo = new OrderDto.DeliveryInfo(
                "이의현", "010-2307-1039", "430-07",
                "안양시 만안구", "박달동", "빠른 배송바랍니다.");

        OrderDto.PlaceOrder placeOrderDto = new OrderDto.PlaceOrder(ar);
        String json = new ObjectMapper().writeValueAsString(placeOrderDto);

        given(orderService.placeOrder(any(OrderCommand.PlaceOrder.class)))
                .willReturn(orderToken);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/order")
                        .content(json)
                        .session(mockSession)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("주문이 완료되었습니다."))
                .andExpect(jsonPath("$.data").value("B12345678"))
                .andDo(print()
                );

        verify(orderService).placeOrder(any(OrderCommand.PlaceOrder.class));
    }
}