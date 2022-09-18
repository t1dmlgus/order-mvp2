package dev.t1dmlgus.order.domain;

import dev.t1dmlgus.common.util.Money;
import dev.t1dmlgus.common.util.MoneyConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Convert;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@ToString
@Getter
public class OrderLine {

    private String productToken;

    @Convert(converter = MoneyConverter.class)
    private Money price;

    private int quantity;

    @Convert(converter = MoneyConverter.class)
    private Money amounts;


    public OrderLine(String productToken, Money price, int quantity) {
        this.productToken = productToken;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    private Money calculateAmounts() {
        return price.multiply(quantity);
    }
}
