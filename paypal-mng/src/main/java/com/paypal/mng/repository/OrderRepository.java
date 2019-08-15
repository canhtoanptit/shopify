package com.paypal.mng.repository;

import com.paypal.mng.domain.Order;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderNumberIn(List<Integer> orderNumbers);
}
