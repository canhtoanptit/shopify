package com.paypal.mng.worker;

import com.paypal.mng.service.dto.shopify.ShopifyOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderDailyCacheManager {
    private final Logger logger = LoggerFactory.getLogger(OrderDailyCacheManager.class);

    private ConcurrentHashMap<String, Set<ShopifyOrder>> orderDailyCache = new ConcurrentHashMap<>();

    public void addOrder(String storeName, List<ShopifyOrder> orderList) {
        Set<ShopifyOrder> orders = orderDailyCache.get(storeName);
        if (orders == null) {
            logger.warn("orders null");
            Comparator<ShopifyOrder> com = Comparator.comparingLong(ShopifyOrder::getId);
            orders = new TreeSet<>(com.reversed());
            orders.addAll(orderList);
            orderDailyCache.put(storeName, orders);
        }
        orders.addAll(orderList);
    }

    public List<ShopifyOrder> getAllOrder() {
        List<ShopifyOrder> rs = new ArrayList<>();
        orderDailyCache.forEach((k, v) -> rs.addAll(v));
        return rs;
    }

    public void clearCache() {
        orderDailyCache.forEach((k, v) -> v.clear());
    }
}
