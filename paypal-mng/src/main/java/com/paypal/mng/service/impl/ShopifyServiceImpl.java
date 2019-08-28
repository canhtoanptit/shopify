package com.paypal.mng.service.impl;

import com.paypal.mng.service.ShopifyService;
import com.paypal.mng.service.dto.StoreDTO;
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

    public OrderList getOrdersBy(String baseUrl, String username, String password, Long sinceId) {
        HashMap<String, String> uriParams = new HashMap<>();
        uriParams.put("limit", "250");
        uriParams.put("status", "any");
        uriParams.put("fulfillment_status", "shipped");
        if (sinceId != null) {
            uriParams.put("since_id", String.valueOf(sinceId));
        }
//        uriParams.put("updated_at_min", DateTimeUtil.atStartOfDay(new Date()));
        uriParams.put("updated_at_max", DateTimeUtil.atEndOfDay(new Date()));
        uriParams.put("fields", JsonConstants.ID + "," + JsonConstants.FULFILLMENTS + "," + JsonConstants.ORDER_NUMBER
            + "," + JsonConstants.NAME);
        UriComponentsBuilder uri = RestUtil.buildQuery(baseUrl, uriParams);
        return shopifyApiClient.getOrders(uri.toUriString(), username, password);
    }

    public TransactionList getTransactions(String baseUrl, String username, String password) {
        HashMap<String, String> uriParams = new HashMap<>();
        uriParams.put("fields", JsonConstants.ID + "," + JsonConstants.AUTHORIZATION + "," + JsonConstants.ORDER_ID
            + "," + JsonConstants.STATUS);
        UriComponentsBuilder uri = RestUtil.buildQuery(baseUrl, uriParams);
        return shopifyApiClient.getTransactions(uri.toUriString(), username, password);
    }

    @Override
    public OrderList getOrderExternal(StoreDTO storeDTO) {
        return shopifyApiClient.getListOrder(storeDTO);
    }

    @Override
    public OrderList getOrderExternalBatch(StoreDTO storeDTO) {
        return shopifyApiClient.getListOrderBatch(storeDTO);
    }

    @Override
    public OrderList getOrderPartialExternal(StoreDTO storeDTO) {
        return shopifyApiClient.getListOrderPartial(storeDTO);
    }
}
