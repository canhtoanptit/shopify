package com.paypal.mng.service.external;

import com.paypal.mng.service.dto.StoreDTO;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.ShopifyOrder;
import com.paypal.mng.service.dto.shopify.TransactionList;
import com.paypal.mng.service.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShopifyApiClientImpl implements ShopifyApiClient {
    private final Logger log = LoggerFactory.getLogger(ShopifyApiClientImpl.class);
    private final RestTemplate restTemplate;

    public ShopifyApiClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public OrderList getOrders(String url, String username, String password) {
        ResponseEntity<OrderList> rs = restTemplate.exchange(url,
            HttpMethod.GET, new HttpEntity<OrderList>(RestUtil.createHeaders(username, password)), OrderList.class);
        return rs.getBody();
    }

    public TransactionList getTransactions(String url, String username, String password) {
        ResponseEntity<TransactionList> rs = restTemplate.exchange(url, HttpMethod.GET,
            new HttpEntity<TransactionList>(RestUtil.createHeaders(username, password)), TransactionList.class);
        return rs.getBody();
    }

    @Override
    public OrderList getListOrder(StoreDTO storeDTO) {
        return restTemplate.postForObject("http://localhost:3000/api/order/internal", storeDTO, OrderList.class);
    }

    @Override
    public OrderList getListOrderBatch(StoreDTO storeDTO) {
        log.info("process getListOrderBatch");
        return restTemplate.postForObject("http://localhost:3000/api/order/internal/batch", storeDTO, OrderList.class);
    }

    @Override
    public OrderList getListOrderPartial(StoreDTO storeDTO) {
        log.info("Process getListOrderPartial");
        return restTemplate.postForObject("http://localhost:3000/api/order/internal/partial", storeDTO, OrderList.class);
    }

    @Override
    public OrderList getOrderInDay(String url, String username, String password) {
        ResponseEntity<OrderList> rs = restTemplate.exchange(url,
            HttpMethod.GET, new HttpEntity<OrderList>(RestUtil.createHeaders(username, password)), OrderList.class);
        log.info("Get rs from shopify with hhtp status {}", rs.getStatusCodeValue());
        return rs.getBody();
    }

    @Override
    public ShopifyOrder findById(StoreDTO storeDTO) {
        String url = storeDTO.getShopifyApiUrl() + "orders.json?ids=" + storeDTO.getId();
        ResponseEntity<ShopifyOrder> rs = restTemplate.exchange(url,
            HttpMethod.GET, new HttpEntity<ShopifyOrder>(RestUtil.createHeaders(storeDTO.getShopifyApiKey(), storeDTO.getShopifyApiPassword())),
            ShopifyOrder.class);
        return rs.getBody();
    }
}
