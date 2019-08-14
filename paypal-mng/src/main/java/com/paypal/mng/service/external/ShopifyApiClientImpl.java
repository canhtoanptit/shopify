package com.paypal.mng.service.external;

import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.Transaction;
import com.paypal.mng.service.dto.shopify.TransactionList;
import com.paypal.mng.service.util.RestUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShopifyApiClientImpl implements ShopifyApiClient {

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
}
