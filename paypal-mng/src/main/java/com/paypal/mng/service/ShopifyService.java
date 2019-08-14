package com.paypal.mng.service;

import com.paypal.mng.service.dto.shopify.OrderList;

public interface ShopifyService {

    OrderList getOrdersBy();
}
