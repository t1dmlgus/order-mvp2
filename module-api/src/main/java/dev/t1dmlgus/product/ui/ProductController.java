package dev.t1dmlgus.product.ui;


import dev.t1dmlgus.common.util.response.CommonResponse;
import dev.t1dmlgus.product.command.application.ProductCommand;
import dev.t1dmlgus.product.command.application.ProductInfo;
import dev.t1dmlgus.product.command.application.ProductService;
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
