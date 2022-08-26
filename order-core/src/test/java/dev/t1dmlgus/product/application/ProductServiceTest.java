package dev.t1dmlgus.product.application;

import dev.t1dmlgus.product.domain.Product;
import dev.t1dmlgus.product.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Product testProduct;
    private String testProductToken;

    @BeforeEach
    void setUp() {
        testProduct = Product.newInstance(
                "그리스 로마 신화 9 - 가장 아름다운 여신", 13_000, 70);
        testProductToken = testProduct.getProductToken();
    }

    @Test
    void registerProduct_returns_productToken_start_with_P(){

        // given
        ProductCommand.RegisterProduct registerProductCommand = ProductCommand.RegisterProduct.builder()
                .name("그리스로마신화8 - 오르페우스의사랑")
                .price(13000)
                .stock(80)
                .build();

        Product product = registerProductCommand.toProduct();
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(product);

        // when
        ProductInfo.ProductToken registerProduct = productService.register(registerProductCommand);

        // then
        Assertions.assertThat(registerProduct.getProductToken().substring(0, 1)).isEqualTo("P");
    }

    @Test
    void view_product_returns_productDetails(){

        // given
        Mockito.when(productRepository.findByProductToken(any(String.class))).thenReturn(Optional.ofNullable(testProduct));

        // when
        ProductInfo.ProductDetail productDetail = productService.view(testProductToken);

        // then
        Assertions.assertThat(productDetail.getProductToken()).isEqualTo(testProductToken);
    }

    @Test
    void view_product_not_exist_productToken_returns_exception(){

        // given
        // when

        // then
        Assertions.assertThatThrownBy(() -> productService.view(testProductToken))
                .isInstanceOf(Exception.class)
                .hasMessage("해당 상품이 없습니다.");
    }

}