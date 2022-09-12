package dev.t1dmlgus.order.application;


import dev.t1dmlgus.order.domain.OrderRepository;
import dev.t1dmlgus.product.domain.Product;
import dev.t1dmlgus.product.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderLineFactory orderLineFactory;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    private Product testProduct;
    private String productToken;

    private ArrayList<OrderCommand.OrderProduct> ar = new ArrayList<>();

    @BeforeEach
    public void before(){

        Product beforeProduct = Product.newInstance("셜록홈즈", 14_000, 40);
        testProduct = productRepository.save(beforeProduct);
        productToken = beforeProduct.getProductToken();

        ar.add(OrderCommand.OrderProduct.newInstance(productToken, 1));
    }


    @Test
    void placeOrder_100_order_at_the_same_time() throws InterruptedException {

        String memberToken = "M12345678";

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int k = 0; k < threadCount; k++) {
            executorService.submit(() -> {
                try {
                    orderService.placeOrder(OrderCommand.PlaceOrder.newInstance(ar, memberToken, null));
                } finally {
                    latch.countDown();

                }
            });
        }

        latch.await();
//
        Product product = productRepository.findByProductToken(productToken)
                .orElseThrow(()-> new RuntimeException("해당 상품은 존재하지 않습니다."));

        System.out.println("product.getStock() = " + product.getStock());
        Assertions.assertThat(product.getStock()).isEqualTo(0);
    }


}