package com.paypal.mng.service.external;

import com.paypal.mng.service.dto.shopify.OrderList;

public interface ShopifyApiClient {
    int MAXIMUM_RETURNED_RESULTS = 250;

    OrderList getOrder(String url, String username, String password);
}

