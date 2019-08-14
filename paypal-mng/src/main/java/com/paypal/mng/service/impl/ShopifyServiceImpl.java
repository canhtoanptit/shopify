package com.paypal.mng.service.impl;

import com.paypal.mng.service.ShopifyService;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.external.ShopifyApiClient;
import org.springframework.stereotype.Service;

@Service
public class ShopifyServiceImpl implements ShopifyService {

    private final ShopifyApiClient shopifyApiClient;

    public ShopifyServiceImpl(ShopifyApiClient shopifyApiClient) {
        this.shopifyApiClient = shopifyApiClient;
    }

    public OrderList getOrdersBy() {
        OrderList rs = shopifyApiClient.getOrder("", "id", "any");
        System.out.println(rs.getOrders());
        return rs;
    }
}
