package dev.t1dmlgus.product.command.application;


import dev.t1dmlgus.common.error.exception.DuplicateException;
import dev.t1dmlgus.common.error.exception.NotFoundException;
import dev.t1dmlgus.common.error.type.ErrorType;
import dev.t1dmlgus.product.command.domain.Product;
import dev.t1dmlgus.product.command.domain.ProductRepository;
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
        if (isExistProduct(product.getName())) {
            throw new DuplicateException(ErrorType.DUPLICATED_PRODUCT);
        }
        Product savedProduct = productRepository.save(product);
        return ProductInfo.ProductToken.newInstance(savedProduct);
    }

    @Transactional(readOnly = true)
    public boolean isExistProduct(String productName){
        return productRepository.existsByName(productName);
    }

    @Transactional
    public ProductInfo.ProductDetail view(String productToken) {
        Product product = productRepository.findByProductToken(productToken)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND_PRODUCT));
        return ProductInfo.ProductDetail.newInstance(product);
    }
}