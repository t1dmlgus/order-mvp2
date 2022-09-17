package dev.t1dmlgus.order.application;

import org.springframework.stereotype.Service;

@Service
public class OrderFacade {

    private OrderService orderService;

    public OrderFacade(OrderService orderService) {
        this.orderService = orderService;
    }

    public void placeOrder(OrderCommand.PlaceOrder placeOrder) throws InterruptedException {

        while (true){
            try {
                orderService.placeOrder(placeOrder);

                break;
            } catch (Exception e) {
                Thread.sleep(50);

            }
        }

    }
}
