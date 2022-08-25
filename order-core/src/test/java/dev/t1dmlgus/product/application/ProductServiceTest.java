package dev.t1dmlgus.product.application;

import dev.t1dmlgus.product.domain.Product;
import dev.t1dmlgus.product.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;


    @Test
    void registerProduct_returns_productToken_start_with_P(){

        // given
        ProductCommand.registerProduct registerProductCommand = ProductCommand.registerProduct.builder()
                .name("그리스로마신화8 - 오르페우스의사랑")
                .price(13000)
                .stock(80)
                .build();

        Product product = registerProductCommand.toProduct();
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);

        // when
        String productToken = productService.registerProduct(registerProductCommand);

        // then
        Assertions.assertThat(productToken.substring(0, 1)).isEqualTo("P");

    }

}