package dev.t1dmlgus.ordermvp2.order.application;

import dev.t1dmlgus.common.Money;
import dev.t1dmlgus.order.application.OrderCommand;
import dev.t1dmlgus.order.application.OrderLineFactory;
import dev.t1dmlgus.order.application.OrderService;
import dev.t1dmlgus.order.domain.Order;
import dev.t1dmlgus.order.domain.OrderLine;
import dev.t1dmlgus.order.domain.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderLineFactory orderLineFactory;
    @Mock
    private OrderRepository orderRepository;


    @Test
    @DisplayName("주문 완료 성공 시, 반환하는 토큰의 시작은 A이다.")
    void placeOrder_returns_orderToken_startWith_A() {

        // given
        int productQuantity = 3;
        String memberToken = "M12345678";
        OrderCommand.OrderDeliveryInfo orderDeliveryInfo=
                OrderCommand.OrderDeliveryInfo.builder()
                        .address1("안양시")
                        .address2("만안구")
                        .zipCode("430-25")
                        .msg("주문하신 음료입니다.")
                        .receiverName("이의현")
                        .receiverPhoneNum("010-2307-1039")
                        .build();

        ArrayList<OrderCommand.OrderProduct> ar = new ArrayList<>();
        IntStream.range(1, 4).forEach(i-> {
            ar.add(OrderCommand.OrderProduct.newInstance("B"+i, productQuantity));
        });
        OrderCommand.PlaceOrder placeOrder = OrderCommand.PlaceOrder.newInstance(ar, memberToken, orderDeliveryInfo);

        ArrayList<OrderLine> orderLines = new ArrayList<>();

        orderLines.add(new OrderLine("B111111",new Money(3000),3));
        orderLines.add(new OrderLine("B222222",new Money(2000),3));
        orderLines.add(new OrderLine("B333333",new Money(5000),3));


        Order order = new Order(orderLines, memberToken, placeOrder.getDeliveryInfo());

        // when
        Mockito.when(orderLineFactory.store(ar)).thenReturn(orderLines);
        Mockito.when(orderRepository.save(any(Order.class))).thenReturn(order);
        String orderToken = orderService.placeOrder(placeOrder);

        // then
        Assertions.assertThat(orderToken.substring(0, 1)).isEqualTo("A");
    }
}
