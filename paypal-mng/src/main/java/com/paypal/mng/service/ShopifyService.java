package com.paypal.mng.service;

import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.TransactionList;

public interface ShopifyService {

    OrderList getOrdersBy(String baseUrl, String username, String password);

    TransactionList getTransactions(String baseUrl, String username, String password);
}
