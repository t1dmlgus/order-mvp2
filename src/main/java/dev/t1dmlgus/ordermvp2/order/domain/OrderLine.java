package dev.t1dmlgus.ordermvp2.order.domain;

import dev.t1dmlgus.ordermvp2.common.Money;
import lombok.Getter;


@Getter
public class OrderLine {

    private final Long productId;
    private final Money price;
    private final int quantity;
    private final Money amounts;


    public OrderLine(Long productId, Money price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    private Money calculateAmounts() {
        return price.multiply(quantity);
    }
}
