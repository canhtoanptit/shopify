package com.quyenbeo.shopifym.web.rest;

import com.quyenbeo.shopifym.service.dto.OrderDTO;
import com.quyenbeo.shopifym.web.rest.util.HeaderUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
public class TestController {

    private final String SHOPIFY_URL = "https://vuzila.myshopify.com/admin/api/2019-04/orders.json?limit=10&fields=name,created_at,total_price,line_items";
    private final String API_KEY = "b2512f8ab6040a122505b9807082fd5f";
    private final String PASSWORD = "94db542ec643724f96eabc8d1ab34e49";

    private final RestTemplate restTemplate;

    public TestController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("")
    public void getListOrders() {
        restTemplate.exchange(SHOPIFY_URL, HttpMethod.GET, new HttpEntity<OrderDTO>(HeaderUtil.createHeaders(API_KEY, PASSWORD)), OrderDTO.class);
    }
}
