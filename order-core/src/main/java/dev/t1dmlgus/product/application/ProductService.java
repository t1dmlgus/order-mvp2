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
    public String register(ProductCommand.RegisterProduct registerProduct){
        Product product = registerProduct.toProduct();
        Product savedProduct = productRepository.save(product);
        return savedProduct.getProductToken();
    }
}
