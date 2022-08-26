package dev.t1dmlgus.product.application;


import dev.t1dmlgus.product.domain.Product;
import dev.t1dmlgus.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductInfo.ProductToken register(ProductCommand.RegisterProduct registerProduct){
        Product product = registerProduct.toProduct();
        Product savedProduct = productRepository.save(product);
        return ProductInfo.ProductToken.newInstance(savedProduct);
    }


    @Transactional(readOnly = true)
    public ProductInfo.ProductDetail view(String productToken) {
        Product product = productRepository.findByProductToken(productToken)
                .orElseThrow(() -> new RuntimeException("해당 상품이 없습니다."));
        return ProductInfo.ProductDetail.newInstance(product);
    }
}