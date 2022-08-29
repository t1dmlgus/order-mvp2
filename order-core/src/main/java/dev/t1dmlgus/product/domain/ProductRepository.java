package dev.t1dmlgus.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<Product> findByProductToken(String productToken);
}
