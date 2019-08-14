package com.paypal.mng.web.rest;

import com.paypal.mng.service.ShopifyService;
import com.paypal.mng.service.dto.shopify.OrderList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    private final ShopifyService shopifyService;

    public TestController(ShopifyService shopifyService) {
        this.shopifyService = shopifyService;
    }

    @GetMapping("/test")
    public OrderList getOrder() {
        return shopifyService.getOrdersBy();
    }
}
