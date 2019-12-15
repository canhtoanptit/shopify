package com.paypal.mng.worker;

import com.paypal.mng.service.dto.shopify.ShopifyOrder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class OrderDailyCacheManagerTest {
    OrderDailyCacheManager orderDailyCacheManager = new OrderDailyCacheManager();

    @Test
    void testUpdateCache() {
        ShopifyOrder order1 = new ShopifyOrder();
        order1.setId(1);
        ShopifyOrder order2 = new ShopifyOrder();
        order2.setId(2);
        ShopifyOrder order3 = new ShopifyOrder();
        order3.setId(4);
        ShopifyOrder order4 = new ShopifyOrder();
        order4.setId(3);
        orderDailyCacheManager.addOrder("actimazo", Arrays.asList(order2, order1));
        orderDailyCacheManager.addOrder("actimazo", Arrays.asList(order3, order4));
        List<ShopifyOrder> data = orderDailyCacheManager.getAllOrder();
        System.out.println(data);
    }
}
