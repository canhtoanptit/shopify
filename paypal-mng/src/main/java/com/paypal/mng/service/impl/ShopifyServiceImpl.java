package com.paypal.mng.service.impl;

import com.paypal.mng.service.ShopifyService;
import com.paypal.mng.service.dto.shopify.JsonConstants;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.TransactionList;
import com.paypal.mng.service.external.ShopifyApiClient;
import com.paypal.mng.service.util.DateTimeUtil;
import com.paypal.mng.service.util.RestUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.HashMap;

@Service
public class ShopifyServiceImpl implements ShopifyService {

    private final ShopifyApiClient shopifyApiClient;

    public ShopifyServiceImpl(ShopifyApiClient shopifyApiClient) {
        this.shopifyApiClient = shopifyApiClient;
    }

    public OrderList getOrdersBy(String baseUrl, String username, String password) {
        HashMap<String, String> uriParams = new HashMap<>();
        uriParams.put("limit", "10");
        uriParams.put("status", "any");
        uriParams.put("fulfillment_status", "shipped");
//        uriParams.put("updated_at_min", DateTimeUtil.atStartOfDay(new Date()));
        uriParams.put("updated_at_max", DateTimeUtil.atEndOfDay(new Date()));
        uriParams.put("fields", JsonConstants.ID + "," + JsonConstants.FULFILLMENTS + "," + JsonConstants.ORDER_NUMBER);
        UriComponentsBuilder uri = RestUtil.buildQuery(baseUrl, uriParams);
        return shopifyApiClient.getOrders(uri.toUriString(), username, password);
    }

    public TransactionList getTransactions(String baseUrl, String username, String password) {
        HashMap<String, String> uriParams = new HashMap<>();
        uriParams.put("fields", JsonConstants.ID + "," + JsonConstants.AUTHORIZATION + "," + JsonConstants.ORDER_ID);
        UriComponentsBuilder uri = RestUtil.buildQuery(baseUrl, uriParams);
        return shopifyApiClient.getTransactions(uri.toUriString(), username, password);
    }
}