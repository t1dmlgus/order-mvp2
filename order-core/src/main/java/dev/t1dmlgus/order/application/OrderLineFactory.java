package dev.t1dmlgus.order.application;

import dev.t1dmlgus.order.domain.OrderLine;
import dev.t1dmlgus.product.domain.Product;
import dev.t1dmlgus.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
                            .orElseThrow(()-> new RuntimeException("해당 상품이 존재하지 않습니다."));

                    // 재고감소(상품)
                    int checkStock = product.checkStock(i.getQuantity());
                    productRepository.saveAndFlush(product);

                    return new OrderLine(
                            i.getProductToken(),
                            product.getPrice(),
                            i.getQuantity());
                })
                .collect(Collectors.toList());
    }

}