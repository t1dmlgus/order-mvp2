package dev.t1dmlgus.ordermvp2.product.domain;


import dev.t1dmlgus.ordermvp2.AbstractEntity;
import dev.t1dmlgus.ordermvp2.common.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class Product extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Money price;

    private ProductStatus status;

    @Getter
    @RequiredArgsConstructor
    public enum ProductStatus {
        PREPARE("판매준비중"),
        ON_SALE("판매중"),
        OFF_SALE("핀매종료");

        private final String description;
    }


    public Product(String name, Money price) {
        this.name = name;
        this.price = price;
        this.status = ProductStatus.PREPARE;
    }

    public void changeProductOnSale(){
        this.status = ProductStatus.ON_SALE;
    }


    public void changePrice(Money changedPrice){
        this.price = price.add(changedPrice);
    }

    public void changeProductName(String productName){
        this.name = productName;
    }

}
