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
                    Product product = productRepository.findByProductToken(i.getProductToken())
                            .orElseThrow(() -> new RuntimeException("해당 상품이 없습니다."));
                    return new OrderLine(product.getProductToken(), product.getPrice(), i.getQuantity());
                }).collect(Collectors.toList());
    }
}
