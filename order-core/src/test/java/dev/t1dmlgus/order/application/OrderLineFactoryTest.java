package dev.t1dmlgus.order.application;

import dev.t1dmlgus.order.domain.OrderLine;
import dev.t1dmlgus.product.domain.Product;
import dev.t1dmlgus.product.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest
class OrderLineFactoryTest {

    @Autowired
    private OrderLineFactory orderLineFactory;

    @Autowired
    private ProductRepository productRepository;

    private Product product;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void before(){

        product = Product.newInstance("셜록홈즈", 14_000, 80);
        product1 = Product.newInstance("셜록홈즈2", 14_000, 30);
        product2 = Product.newInstance("셜록홈즈3", 14_000, 50);
        productRepository.save(product);
        productRepository.save(product1);
        productRepository.save(product2);
    }

    @AfterEach
    public void after(){
        productRepository.delete(product);
    }


    @DisplayName("주문 후 상품 재고 감소 테스트")
    @Test
    void decrease_orderLine_stock() {

        List<OrderCommand.OrderProduct> ar = new ArrayList<>();
        ar.add(OrderCommand.OrderProduct.newInstance(product.getProductToken(), 3));
        ar.add(OrderCommand.OrderProduct.newInstance(product1.getProductToken(), 7));
        ar.add(OrderCommand.OrderProduct.newInstance(product2.getProductToken(), 2));

        List<OrderLine> store = orderLineFactory.store(ar);

        product = productRepository.findByProductToken(product.getProductToken()).orElseThrow();
        product1 = productRepository.findByProductToken(product1.getProductToken()).orElseThrow();
        product2 = productRepository.findByProductToken(product2.getProductToken()).orElseThrow();

        Assertions.assertThat(product.getStock()).isEqualTo(77);
        Assertions.assertThat(product1.getStock()).isEqualTo(23);
        Assertions.assertThat(product2.getStock()).isEqualTo(48);
    }




}