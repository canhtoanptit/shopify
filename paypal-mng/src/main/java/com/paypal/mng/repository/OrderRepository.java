package com.paypal.mng.repository;

import com.paypal.mng.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(Integer orderNumbers);

    Page<Order> findByOrderNameContaining(String orderName, Pageable pageable);

    Optional<Order> findByOrderName(String orderName);

    Optional<Order> findByShopifyOrderId(Long shopifyOrderId);
}
