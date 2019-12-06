package com.paypal.mng.service;

import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.external.ShopifyApiClient;
import com.paypal.mng.service.external.ShopifyApiClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

public class ShopifyApiClientTest {

    ShopifyApiClient shopifyApiClient;

    @BeforeEach
    void init() {
        shopifyApiClient = new ShopifyApiClientImpl(new RestTemplate());
    }

    @Test
    void getOrderDaily() {
        OrderList rs = shopifyApiClient.getOrderInDay("https://actimazo.myshopify.com/admin/api/2019-07/orders.json",
            "e79e8ddd6e77536093f7f4931c5f141e",
            "f8cc5732099fd4e52d92709f3cdd8fae");
        System.out.println(rs);
    }
}
