package dev.t1dmlgus.order.infrastructure;

import dev.t1dmlgus.common.error.ErrorType;
import dev.t1dmlgus.common.error.exception.NotFoundException;
import dev.t1dmlgus.order.application.OrderCommand;
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
                .map(orderProduct -> {
                    // 상품 토큰
                    String productToken = orderProduct.getProductToken();
                    // 상품 개수
                    int quantity = orderProduct.getQuantity();
                    // 상품
                    Product product = productRepository.findProductPessimisticLock(productToken)
                            .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND_PRODUCT));

                    // 재고감소(상품)
                    product.checkStock(quantity);

                    return new OrderLine(productToken, product.getPrice(), quantity);
                }).collect(Collectors.toList());
    }
}