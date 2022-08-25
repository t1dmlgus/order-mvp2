package dev.t1dmlgus.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.t1dmlgus.product.application.ProductCommand;
import dev.t1dmlgus.product.application.ProductService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void placeOrder() throws Exception {

        //given
        String productToken = "P12345678";
        ProductDto.RegisterProduct registerProductDto =
                new ProductDto.RegisterProduct("그리스로마신화8", 13_000, 80);
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
                .andExpect(jsonPath("$.data").value("P12345678"))
                .andDo(print()
                );

        verify(productService).register(any(ProductCommand.RegisterProduct.class));
    }
}