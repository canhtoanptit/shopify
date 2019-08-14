package com.paypal.mng.web.rest;

import com.paypal.mng.service.ShopifyService;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.TransactionList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final ShopifyService shopifyService;

    public TestController(ShopifyService shopifyService) {
        this.shopifyService = shopifyService;
    }

    @GetMapping("/order")
    public OrderList getOrder() {
        return shopifyService.getOrdersBy("https://actimazo.myshopify.com/admin/api/2019-07/orders.json",
            "29e15279ee8564d0c333cba572a090d1", "76ea1ffedf641d9a3dfa6f0e93928bdf");
    }

    @GetMapping("/transaction")
    public TransactionList getTransaction() {
        return shopifyService.getTransactions("https://actimazo.myshopify.com/admin/api/2019-07/orders/1339736129630/transactions.json",
            "29e15279ee8564d0c333cba572a090d1", "76ea1ffedf641d9a3dfa6f0e93928bdf");
    }
}
