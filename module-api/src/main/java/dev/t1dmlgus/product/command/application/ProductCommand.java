package dev.t1dmlgus.product.command.application;

import dev.t1dmlgus.product.command.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ProductCommand {

    @ToString
    @Getter
    public static class RegisterProduct {

        private final String name;
        private final int price;
        private final int stock;

        @Builder
        private RegisterProduct(String name, int price, int stock) {
            this.name = name;
            this.price = price;
            this.stock = stock;
        }

        public static RegisterProduct newInstance(String name, int price, int stock){
            return RegisterProduct.builder()
                    .name(name)
                    .price(price)
                    .stock(stock)
                    .build();
        }

        public Product toProduct(){
            return Product.newInstance(name, price, stock);
        }
    }


}
