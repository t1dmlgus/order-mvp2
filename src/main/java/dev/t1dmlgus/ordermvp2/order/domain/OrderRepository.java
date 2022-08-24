package dev.t1dmlgus.ordermvp2.order.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderToken(String orderToken);
}
