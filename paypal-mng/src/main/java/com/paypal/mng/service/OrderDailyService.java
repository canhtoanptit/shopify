package com.paypal.mng.service;

import com.paypal.mng.service.dto.OrderDailyDTO;
import com.paypal.mng.service.dto.shopify.LineItem;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.ShopifyOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDailyService {
    private final Logger logger = LoggerFactory.getLogger(OrderDailyService.class);

    private final ShopifyService shopifyService;

    public OrderDailyService(ShopifyService shopifyService) {
        this.shopifyService = shopifyService;
    }

    public List<ShopifyOrder> findAll() {
        logger.info("Start daily logging");
        OrderList allOrders = shopifyService.getOrderDaily();
        logger.info("Size of orders {}", allOrders.getOrders().size());
        return allOrders.getOrders();
    }

    public Optional<OrderDailyDTO> findById(Long id) {
        return null;
    }

}
