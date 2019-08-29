package com.paypal.mng.service;

import com.paypal.mng.domain.Order;
import com.paypal.mng.service.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.paypal.mng.domain.Order}.
 */
public interface OrderService {

    /**
     * Save a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    OrderDTO save(OrderDTO orderDTO);

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" order.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderDTO> findOne(Long id);

    /**
     * Delete the "id" order.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


    /**
     * get existed order by order number
     *
     * @param orderNumbers
     * @return
     */
    Optional<Order> findByOrderNumber(Integer orderNumbers);

    /**
     * get existed order by order name
     *
     * @param orderName
     * @return
     */
    Page<Order> findByOrderNameContain(String orderName);

    /**
     * get existed order by order name
     *
     * @param orderName
     * @return
     */
    Optional<Order> findByOrderName(String orderName);

    /**
     * get existed order by order name
     *
     * @param shopifyOrderId
     * @return
     */
    Optional<Order> findByShopifyOrderId(Long shopifyOrderId);

    Page<OrderDTO> findAllByOrderName(String orderName, Pageable pageable);
}
