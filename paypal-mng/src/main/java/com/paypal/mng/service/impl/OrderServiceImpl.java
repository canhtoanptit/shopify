package com.paypal.mng.service.impl;

import com.paypal.mng.service.OrderService;
import com.paypal.mng.domain.Order;
import com.paypal.mng.repository.OrderRepository;
import com.paypal.mng.service.dto.OrderDTO;
import com.paypal.mng.service.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    /**
     * Save a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request to save Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable)
            .map(OrderDTO::toDto);
    }


    /**
     * Get one order by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        return orderRepository.findById(id)
            .map(orderMapper::toDto);
    }

    /**
     * Delete the order by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }

    @Override
    public Optional<Order> findByOrderNumber(Integer orderNumbers) {
        return orderRepository.findByOrderNumber(orderNumbers);
    }

    /**
     * get existed order by order name
     *
     * @param orderName
     * @return
     */
    @Override
    public Page<Order> findByOrderNameContain(String orderName) {
        return orderRepository.findByOrderNameContaining(orderName, PageRequest.of(0, 20));
    }

    /**
     * get existed order by order name
     *
     * @param orderName
     * @return
     */
    @Override
    public Optional<Order> findByOrderName(String orderName) {
        return orderRepository.findByOrderName(orderName);
    }

    /**
     * get existed order by order name
     *
     * @param shopifyOrderId
     * @return
     */
    @Override
    public Optional<Order> findByShopifyOrderId(Long shopifyOrderId) {
        return orderRepository.findByShopifyOrderId(shopifyOrderId);
    }

    @Override
    public Page<OrderDTO> findAllByOrderName(String orderName, Pageable pageable) {
        return orderRepository.findByOrderNameContaining(orderName, pageable)
            .map(orderMapper::toDto);
    }
}
