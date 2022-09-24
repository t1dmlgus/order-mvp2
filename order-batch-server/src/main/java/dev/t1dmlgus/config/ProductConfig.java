package dev.t1dmlgus.config;

import dev.t1dmlgus.product.domain.Product;
import dev.t1dmlgus.product.domain.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class ProductConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final ProductRepository productRepository;

    public ProductConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, EntityManagerFactory entityManagerFactory, ProductRepository productRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.entityManagerFactory = entityManagerFactory;
        this.productRepository = productRepository;
    }

//    @Bean
//    public Job productJob() {
//        return this.jobBuilderFactory.get("productTaskletJob")
//                .incrementer(new RunIdIncrementer())
//                .start(this.saveProductStep())
//                .build();
//
//    }

    @Bean
    public Step saveProductStep(){
        return this.stepBuilderFactory.get("saveProductStep")
                .tasklet(new SaveProductTasklet(productRepository))
                .build();

    }

    @Bean
    public Job productJob2() throws Exception {
        return this.jobBuilderFactory.get("productChunkJob")
                .incrementer(new RunIdIncrementer())
                .start(this.productStep())
                .build();

    }

    @Bean
    public Step productStep() throws Exception {
        return stepBuilderFactory.get("productChunkStep")
                .<Product, Product>chunk(1_000)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }



    private List<Product> getItems(){

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

    private ItemReader<Product> itemReader(){
        return new CustomItemReader<>(getItems());
    }

    private ItemWriter<Product> itemWriter() throws Exception {

        JpaItemWriter<Product> itemWriter = new JpaItemWriterBuilder<Product>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();

        itemWriter.afterPropertiesSet();
        return itemWriter;
    }

}
