package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderList
{
    @JsonProperty(value = JsonConstants.ORDERS)
    private List<ShopifyOrder> orders;

    public List<ShopifyOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ShopifyOrder> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "OrderList{" +
            "orders=" + orders +
            '}';
    }
}
