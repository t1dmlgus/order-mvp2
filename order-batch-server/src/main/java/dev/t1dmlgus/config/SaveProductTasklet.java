package dev.t1dmlgus.config;

import dev.t1dmlgus.product.domain.Product;
import dev.t1dmlgus.product.domain.ProductRepository;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaveProductTasklet implements Tasklet {

    private final ProductRepository productRepository;

    public SaveProductTasklet(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        List<Product> products = createProduct();

        Collections.shuffle(products);

        productRepository.saveAll(products);

        return RepeatStatus.FINISHED;
    }

    private List<Product> createProduct() {

        List<Product> products = new ArrayList<>();

        // 9개 중(1 ~ 9)
        // 3개(00, 000, 0000)
        int max = 999;
        int min = 1;

        for (int i = 0; i < 50_000; i++) {
            products.add(Product.newInstance(
                    "P"+i,
                    (int) (Math.random() * (max - min) + min)*100
                    ,(int) (Math.random() * (max - min) + min))
            );
        }
        return products;
    }
}
