package dev.t1dmlgus.product.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.t1dmlgus.product.command.application.ProductCommand;
import dev.t1dmlgus.product.command.application.ProductInfo;
import dev.t1dmlgus.product.command.application.ProductService;
import dev.t1dmlgus.product.command.domain.Product;
import dev.t1dmlgus.product.ui.ProductController;
import dev.t1dmlgus.product.ui.ProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
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
    private MockHttpSession mockSession;

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();

        testProduct = Product.newInstance(
                "????????????????????? 9 - ?????? ???????????? ??????", 13_000, 70);
        testProductToken = testProduct.getProductToken();

        mockSession = new MockHttpSession();
        mockSession.setAttribute("LOGIN_MEMBER", "M12345678");
    }

    @Test
    void registerProduct() throws Exception {

        //given
        ProductDto.RegisterProduct registerProductDto =
                new ProductDto.RegisterProduct("????????????????????? 8 - ?????????????????? ??????", 13_000, 80);
        Product product = registerProductDto.toCommand().toProduct();
        ProductInfo.ProductToken productToken = ProductInfo.ProductToken.newInstance(product);

        String json = new ObjectMapper().writeValueAsString(registerProductDto);
        given(productService.register(any(ProductCommand.RegisterProduct.class)))
                .willReturn(productToken);

        //when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/product")
                        .content(json)
                        .session(mockSession)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("????????? ?????????????????????."))

                // ??????, startsWith??? ?????? ?????? ????????? ??????
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
                        .session(mockSession)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("????????? ?????????????????????."))
                .andExpect(jsonPath("$.data.productName").value("????????????????????? 9 - ?????? ???????????? ??????"))
                .andExpect(jsonPath("$.data.productStatus").value("?????????"))
                .andDo(print()
                );

        verify(productService).view(any(String.class));
    }
}