package dev.t1dmlgus.product;


import dev.t1dmlgus.product.application.ProductCommand;
import dev.t1dmlgus.product.application.ProductService;
import dev.t1dmlgus.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/product")
    public ResponseEntity<CommonResponse<String>> registerProduct(@RequestBody ProductDto.RegisterProduct registerProductDto) {

        ProductCommand.RegisterProduct registerProductCommand = registerProductDto.toCommand();
        String productToken = productService.register(registerProductCommand);
        CommonResponse<String> commonResponse = CommonResponse.of(productToken, "상품이 등록되었습니다.");
        return ResponseEntity.ok(commonResponse);
    }
}
