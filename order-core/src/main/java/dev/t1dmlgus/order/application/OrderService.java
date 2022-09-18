package dev.t1dmlgus.order.application;


import dev.t1dmlgus.common.error.ErrorType;
import dev.t1dmlgus.common.error.exception.NotFoundException;
import dev.t1dmlgus.order.domain.Order;
import dev.t1dmlgus.order.domain.OrderLine;
import dev.t1dmlgus.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderLineFactory orderLineFactory;
    private final OrderRepository orderRepository;

    @Transactional
    public String placeOrder(OrderCommand.PlaceOrder placeOrder){
        List<OrderLine> orderLines = orderLineFactory.store(placeOrder.getOrderProducts());
        Order order = Order.newInstance(orderLines, placeOrder.getMemberToken(), placeOrder.getDeliveryInfo());
        Order save = orderRepository.save(order);
        return save.getOrderToken();
    }


    @Transactional(readOnly = true)
    public String viewOrder(String orderToken){
        Order order = orderRepository.findByOrderToken(orderToken)
                .orElseThrow(()->new NotFoundException(ErrorType.NOT_FOUND_ORDER));
        return order.getOrderToken();
    }

}