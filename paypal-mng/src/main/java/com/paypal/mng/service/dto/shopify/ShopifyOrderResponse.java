package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShopifyOrderResponse {

    @JsonProperty(value = "order")
    private ShopifyOrder shopifyOrder;

    public ShopifyOrder getShopifyOrder() {
        return shopifyOrder;
    }

    public void setShopifyOrder(ShopifyOrder shopifyOrder) {
        this.shopifyOrder = shopifyOrder;
    }
}
