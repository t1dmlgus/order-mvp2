package dev.t1dmlgus.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.t1dmlgus.product.application.ProductCommand;
import dev.t1dmlgus.product.application.ProductInfo;
import dev.t1dmlgus.product.application.ProductService;
import dev.t1dmlgus.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product testProduct;
    private String  testProductToken;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        testProduct = Product.newInstance(
                "그리스로마신화 9 - 가장 아름다운 여신", 13_000, 70);
        testProductToken = testProduct.getProductToken();
    }

    @Test
    void registerProduct() throws Exception {

        //given
        ProductDto.RegisterProduct registerProductDto =
                new ProductDto.RegisterProduct("그리스로마신화 8 - 오르페우스의 사랑", 13_000, 80);
        Product product = registerProductDto.toCommand().toProduct();
        ProductInfo.ProductToken productToken = ProductInfo.ProductToken.newInstance(product);

        String json = new ObjectMapper().writeValueAsString(registerProductDto);
        given(productService.register(any(ProductCommand.RegisterProduct.class)))
                .willReturn(productToken);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/product")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("상품이 등록되었습니다."))

                // 추후, startsWith와 같은 검증 테스트 필요
                .andExpect(jsonPath("$.data.productToken").exists())
                .andDo(print()
                );

        verify(productService).register(any(ProductCommand.RegisterProduct.class));
    }

    @Test
    void viewProduct() throws Exception {

        //given
        ProductInfo.ProductDetail productDetail = ProductInfo.ProductDetail.newInstance(testProduct);
        given(productService.view(any(String.class)))
                .willReturn(productDetail);

        //when
        ResultActions resultActions = mockMvc.perform(
                get("/api/v1/product/{productToken}",testProductToken)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("상품을 조회하였습니다."))
                .andExpect(jsonPath("$.data.productName").value("그리스로마신화 9 - 가장 아름다운 여신"))
                .andExpect(jsonPath("$.data.productStatus").value("판매중"))
                .andDo(print()
                );

        verify(productService).view(any(String.class));
    }
}