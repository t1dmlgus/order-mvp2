package dev.t1dmlgus.order.application;


import dev.t1dmlgus.order.domain.OrderLine;
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
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private ArrayList<OrderCommand.OrderProduct> ar = new ArrayList<>();

    String productToken = "P3c6ca394fe91";

    @BeforeEach
    public void before(){

        Product beforePersistenceProduct = Product.newInstance("셜록홈즈", 14_000, 40);
        testProduct = productRepository.saveAndFlush(beforePersistenceProduct);


        ar.add(OrderCommand.OrderProduct.newInstance(productToken, 1));
    }

    @AfterEach
    public void after(){
        productRepository.delete(testProduct);
    }




    @Transactional
    @Test
    void placeOrder_100_order_at_the_same_time() throws InterruptedException {

        String memberToken = "M12345678";

        int threadCount = 3;
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

        Product product = productRepository.findByProductToken(productToken)
                .orElseThrow();

        System.out.println("product.getStock() = " + product.getStock());
        Assertions.assertThat(product.getStock()).isEqualTo(37);
    }


}