package dev.t1dmlgus.order.application;


import dev.t1dmlgus.common.error.ErrorType;
import dev.t1dmlgus.common.error.exception.NotFoundException;
import dev.t1dmlgus.common.error.exception.OutOfException;
import dev.t1dmlgus.common.util.TokenUtil;
import dev.t1dmlgus.product.domain.Product;
import dev.t1dmlgus.product.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;

    private OrderCommand.OrderDeliveryInfo orderDeliveryInfo;
    private Product testProduct;
    private String productToken;
    private String memberToken;

    private MockHttpServletRequest request;


    @BeforeEach
    public void before(){

        Product beforeProduct = Product.newInstance("셜록홈즈", 14_000, 40);
        testProduct = productRepository.save(beforeProduct);
        productToken = testProduct.getProductToken();
        memberToken = TokenUtil.generateToken("member");
        orderDeliveryInfo =
                OrderCommand.OrderDeliveryInfo.newInstance(
                "이의현",
                "010-2307-1039",
                "430-11",
                "경기도 안양시",
                "만안구 박달동",
                "빠른 배송 바랍니다.");


    }

    @AfterEach
    public void after(){
        productRepository.delete(testProduct);
    }


    @DisplayName("상품(재고:40) 3개를 주문하면 상품 재고는 37개가 남는다.")
    @Test
    void productQuantity_3_return_37() throws InterruptedException {

        // given
        int productQuantity = 3;
        OrderCommand.PlaceOrder placeOrder =
                OrderCommand.PlaceOrder.newInstance(
                        List.of(OrderCommand.OrderProduct.newInstance(productToken, productQuantity)),
                        memberToken);

        // when
        orderService.placeOrder(placeOrder);

        // then
        Product product = productRepository.findByName("셜록홈즈")
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND_PRODUCT));
        Assertions.assertThat(product.getStock()).isEqualTo(37);
    }


    @DisplayName("상품(재고:40) 3개씩 동시에 100개를 주문하면 상품 재고는 1개가 남는다.")
    @Test
    void order_100_at_the_same_time_return_1() throws InterruptedException {

        // given
        List<OrderCommand.OrderProduct> orderProducts =
                List.of(OrderCommand.OrderProduct.newInstance(productToken, 3));

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        // when
        for (int k = 0; k < threadCount; k++) {
            executorService.submit(() -> {
                try {
                    OrderCommand.PlaceOrder placeOrder =
                            OrderCommand.PlaceOrder.newInstance(orderProducts, memberToken);
                    orderService.placeOrder(placeOrder);

                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        // then
        Product product = productRepository.findByName("셜록홈즈")
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND_ORDER));
        Assertions.assertThat(product.getStock()).isEqualTo(1);
    }


    @DisplayName("재고가 모자라는 경우 재고 예외가 발생한다.")
    @Test
    void product_order_count_is_large_then_stock_will_return_out_of_exception() throws InterruptedException {

        // given
        List<OrderCommand.OrderProduct> orderProducts =
                List.of(OrderCommand.OrderProduct.newInstance(productToken, 41));
        OrderCommand.PlaceOrder placeOrder =
                OrderCommand.PlaceOrder.newInstance(orderProducts, memberToken);

        // then
        Assertions.assertThatThrownBy(() -> orderService.placeOrder(placeOrder))
                .isInstanceOf(OutOfException.class);

    }
}