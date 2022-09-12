package dev.t1dmlgus.order.application;

import dev.t1dmlgus.order.domain.OrderLine;
import dev.t1dmlgus.product.domain.Product;
import dev.t1dmlgus.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderLineFactory {

    private final ProductRepository productRepository;

    public List<OrderLine> store(List<OrderCommand.OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(i -> {

                    Product product = null;
                    try {

                        System.out.println(" 11 ");
                        product = decrease(i);
                    } catch (InterruptedException e) {
                        System.out.println(" 33 ");
                        throw new RuntimeException(e);
                    }
                    System.out.println(">>>>> product.getStock() = " + product.getStock());
                    return new OrderLine(product.getProductToken(), product.getPrice(), i.getQuantity());
                }).collect(Collectors.toList());
    }

    private Product decrease(OrderCommand.OrderProduct i) throws InterruptedException {
        
        System.out.println(" 22 " );
        String productToken = i.getProductToken();
        System.out.println("productToken = " + productToken);

        Product product = null;
        try {
            product = productRepository.findByProductToken(i.getProductToken())
                    .orElseThrow(() -> new RuntimeException("해당 상품이 없습니다."));

            product.checkStock(i.getQuantity());
            productRepository.saveAndFlush(product);

        } catch (Exception e) {
            Thread.sleep(3);

        }
        
        return product;
    }
}
