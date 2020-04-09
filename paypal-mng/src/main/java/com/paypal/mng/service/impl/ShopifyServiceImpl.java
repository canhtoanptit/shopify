package com.paypal.mng.service.impl;

import com.paypal.mng.domain.Store;
import com.paypal.mng.repository.StoreRepository;
import com.paypal.mng.service.ShopifyService;
import com.paypal.mng.service.dto.StoreDTO;
import com.paypal.mng.service.dto.shopify.JsonConstants;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.ShopifyOrder;
import com.paypal.mng.service.dto.shopify.TransactionList;
import com.paypal.mng.service.external.ShopifyApiClient;
import com.paypal.mng.service.util.DateTimeUtil;
import com.paypal.mng.service.util.RestUtil;
import com.paypal.mng.worker.OrderDailyCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
public class ShopifyServiceImpl implements ShopifyService {
    private final Logger log = LoggerFactory.getLogger(ShopifyServiceImpl.class);

    private final ShopifyApiClient shopifyApiClient;

    private final StoreRepository storeRepository;

    private final OrderDailyCacheManager orderDailyCacheManager;

    public ShopifyServiceImpl(ShopifyApiClient shopifyApiClient, StoreRepository storeRepository,
                              OrderDailyCacheManager orderDailyCacheManager) {
        this.shopifyApiClient = shopifyApiClient;
        this.storeRepository = storeRepository;
        this.orderDailyCacheManager = orderDailyCacheManager;
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
        log.debug("Process getOrderExternal");
        return shopifyApiClient.getListOrder(storeDTO);
    }

    @Override
    public OrderList getOrderExternalBatch(StoreDTO storeDTO) {
        log.debug("Process getOrderExternalBatch");
        return shopifyApiClient.getListOrderBatch(storeDTO);
    }

    @Override
    public OrderList getOrderPartialExternal(StoreDTO storeDTO) {
        log.debug("Process getOrderPartialExternal");
        return shopifyApiClient.getListOrderPartial(storeDTO);
    }

    @Override
    public void getOrderDaily() {
        List<Store> stores = storeRepository.findAll();
        if (stores.isEmpty()) {
            return;
        }

        String startOfDay = DateTimeUtil.atStartOfDay(new Date()) + "-09:00";
        stores.forEach(store -> {
            try {
                String uri = store.getShopifyApiUrl() + "orders.json?created_at_min=" + startOfDay + "&limit=250";
                OrderList orders = shopifyApiClient.getOrderInDay(uri, store.getShopifyApiKey(), store.getShopifyApiPassword());
                orderDailyCacheManager.addOrder(store.getStoreName(), orders.getOrders());
            } catch (Exception ex) {
                log.error("Error when getOrderDaily ", ex);
            }
        });
    }

    @Override
    public ShopifyOrder findById(StoreDTO storeDTO) {
        return shopifyApiClient.findById(storeDTO);
    }
}
