package dev.t1dmlgus.product.command.application;

import dev.t1dmlgus.product.command.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductInfo {

    @Getter
    public static class ProductToken{

        private String productToken;

        @Builder
        private ProductToken(String productToken) {
            this.productToken = productToken;
        }

        public static ProductToken newInstance(Product product){
            return ProductToken.builder()
                    .productToken(product.getProductToken())
                    .build();
        }
    }


    @Getter

    public static class ProductDetail{

        private String productToken;
        private String productName;
        private int price;
        private String productStatus;

        @Builder
        private ProductDetail(String productToken, String productName, int price, String productStatus) {
            this.productToken = productToken;
            this.productName = productName;
            this.price = price;
            this.productStatus = productStatus;
        }

        public static ProductDetail newInstance(Product product){
            return ProductDetail.builder()
                    .productToken(product.getProductToken())
                    .productName(product.getName())
                    .price((int) product.getPrice().getValue())
                    .productStatus(product.getStatus().getDescription())
                    .build();
        }
    }
}