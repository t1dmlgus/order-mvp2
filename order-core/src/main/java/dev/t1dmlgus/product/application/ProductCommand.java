package dev.t1dmlgus.product.application;

import dev.t1dmlgus.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ProductCommand {

    @ToString
    @Getter
    public static class registerProduct{

        private String name;
        private int price;
        private int stock;

        @Builder
        public registerProduct(String name, int price, int stock) {
            this.name = name;
            this.price = price;
            this.stock = stock;
        }

        public Product toProduct(){
            return Product.newInstance(name, price, stock);
        }
    }


}
