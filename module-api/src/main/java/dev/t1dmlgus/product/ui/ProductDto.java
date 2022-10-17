package dev.t1dmlgus.product.ui;

import dev.t1dmlgus.product.command.application.ProductCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

public class ProductDto {

    @NoArgsConstructor
    @ToString
    @Getter
    public static class RegisterProduct {

        @NotBlank(message = "상품명을 입력해주세요.")
        private String name;
        @NotBlank(message = "가격을 입력해주세요.")
        private int price;
        @NotBlank(message = "수량을 입력해주세요.")
        private int stock;

        public RegisterProduct(String name, int price, int stock) {
            this.name = name;
            this.price = price;
            this.stock = stock;
        }

        public ProductCommand.RegisterProduct toCommand() {
            return ProductCommand.RegisterProduct.newInstance(name, price, stock);
        }
    }
}
