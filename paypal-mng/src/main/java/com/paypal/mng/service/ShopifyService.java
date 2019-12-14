package com.paypal.mng.service;

import com.paypal.mng.service.dto.StoreDTO;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.ShopifyOrder;
import com.paypal.mng.service.dto.shopify.TransactionList;

public interface ShopifyService {

    OrderList getOrdersBy(String baseUrl, String username, String password, Long sinceId);

    TransactionList getTransactions(String baseUrl, String username, String password);

    OrderList getOrderExternal(StoreDTO storeDTO);

    OrderList getOrderExternalBatch(StoreDTO storeDTO);

    OrderList getOrderPartialExternal(StoreDTO storeDTO);

    void getOrderDaily();

    ShopifyOrder findById(StoreDTO storeDTO);
}
