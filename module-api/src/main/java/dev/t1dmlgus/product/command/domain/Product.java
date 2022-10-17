package dev.t1dmlgus.product.command.domain;


import dev.t1dmlgus.common.AbstractEntity;
import dev.t1dmlgus.common.error.exception.OutOfException;
import dev.t1dmlgus.common.error.type.ErrorType;
import dev.t1dmlgus.common.util.money.Money;
import dev.t1dmlgus.common.util.money.MoneyConverter;
import dev.t1dmlgus.common.util.token.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Table(name = "products")
@Entity
public class Product extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productToken;

    private String name;

    @Convert(converter = MoneyConverter.class)
    private Money price;

    private int stock;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Getter
    @RequiredArgsConstructor
    public enum ProductStatus {
        ON_SALE("판매중"),
        OFF_SALE("판매종료");

        private final String description;
    }

    @Builder
    private Product(String name, Money price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.status = ProductStatus.ON_SALE;
        this.productToken = Token.generateToken("product");
    }

    public static Product newInstance(String name, int price, int stock) {
        return Product.builder()
                .name(name)
                .price(new Money(price))
                .stock(stock)
                .build();
    }

    public void changeProductOnSale(){
        this.status = ProductStatus.ON_SALE;
    }

    public void changePrice(Money changedPrice){
        this.price = price.plus(changedPrice);
    }

    public void changeProductName(String productName){
        this.name = productName;
    }

    public String checkStock(int quantity) {

        if (stock < quantity) {
            throw new OutOfException(ErrorType.OUT_OF_STOCK);
        }
        stock -= quantity;
        return "";
    }
}