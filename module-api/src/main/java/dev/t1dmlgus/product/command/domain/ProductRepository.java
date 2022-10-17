package dev.t1dmlgus.product.command.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {



    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "1000")})
    @Query("select p from Product p where p.productToken=:productToken")
    Optional<Product> findProductPessimisticLock(@Param("productToken") String productToken);


    Optional<Product> findByProductToken(String productToken);

    Optional<Product> findByName(String name);

    boolean existsByName(String movieTitle);
}
