package dev.t1dmlgus.product;


import dev.t1dmlgus.product.application.ProductCommand;
import dev.t1dmlgus.product.application.ProductInfo;
import dev.t1dmlgus.product.application.ProductService;
import dev.t1dmlgus.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/product")
    public ResponseEntity<CommonResponse<ProductInfo.ProductToken>> registerProduct(@RequestBody ProductDto.RegisterProduct registerProductDto) {

        ProductCommand.RegisterProduct registerProductCommand = registerProductDto.toCommand();
        ProductInfo.ProductToken productToken = productService.register(registerProductCommand);
        CommonResponse<ProductInfo.ProductToken> commonResponse =
                CommonResponse.of(productToken, "상품이 등록되었습니다.");
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("/api/v1/product/{productToken}")
    public ResponseEntity<CommonResponse<ProductInfo.ProductDetail>> viewProduct(@PathVariable String productToken) {

        ProductInfo.ProductDetail productDetail = productService.view(productToken);
        CommonResponse<ProductInfo.ProductDetail> commonResponse =
                CommonResponse.of(productDetail, "상품을 조회하였습니다.");
        return ResponseEntity.ok(commonResponse);
    }
}
