package com.paypal.mng.service.external;

import com.paypal.mng.service.dto.StoreDTO;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.ShopifyOrder;
import com.paypal.mng.service.dto.shopify.TransactionList;

public interface ShopifyApiClient {
    int MAXIMUM_RETURNED_RESULTS = 250;

    OrderList getOrders(String url, String username, String password);

    TransactionList getTransactions(String url, String username, String password);

    OrderList getListOrder(StoreDTO storeDTO);

    OrderList getListOrderBatch(StoreDTO storeDTO);

    OrderList getListOrderPartial(StoreDTO storeDTO);

    OrderList getOrderInDay(String url, String username, String password);

    ShopifyOrder findById(StoreDTO id);
}

