package dev.t1dmlgus.order.application;

import dev.t1dmlgus.order.domain.OrderLine;
import dev.t1dmlgus.product.domain.Product;
import dev.t1dmlgus.product.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderLineFactoryTest {

    @Autowired
    private OrderLineFactory orderLineFactory;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void before(){

        product = Product.newInstance("셜록홈즈", 14_000, 80);
        productRepository.save(product);
    }

    @AfterEach
    public void after(){
        productRepository.delete(product);
    }


    @Test
    void decrease_orderLine_stock() {

        List<OrderCommand.OrderProduct> ar = new ArrayList<>();
        ar.add(OrderCommand.OrderProduct.newInstance(product.getProductToken(), 3));

        List<OrderLine> store = orderLineFactory.store(ar);
        product = productRepository.findByProductToken(product.getProductToken())
                .orElseThrow();

        Assertions.assertThat(product.getStock()).isEqualTo(77);
    }
}