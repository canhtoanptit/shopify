package com.paypal.mng.service;

import com.paypal.mng.service.dto.OrderDailyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderDailyService {
    private final Logger logger = LoggerFactory.getLogger(OrderDailyService.class);

    private final ShopifyService shopifyService;

    public OrderDailyService(ShopifyService shopifyService) {
        this.shopifyService = shopifyService;
    }

    public void findAll() {
        logger.info("Start daily logging");
        shopifyService.getOrderDaily();
    }

    public Optional<OrderDailyDTO> findById(Long id) {
        return null;
    }

}
