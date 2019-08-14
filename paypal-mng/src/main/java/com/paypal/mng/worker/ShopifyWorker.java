package com.paypal.mng.worker;

import com.paypal.mng.service.ShopifyService;
import com.paypal.mng.service.TrackingService;
import com.paypal.mng.service.dto.TrackingDTO;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.TransactionList;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ShopifyWorker {
    private final ShopifyService shopifyService;

    private final TrackingService trackingService;

    public ShopifyWorker(ShopifyService shopifyService, TrackingService trackingService) {
        this.shopifyService = shopifyService;
        this.trackingService = trackingService;
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void process() {
        System.out.println(Instant.now().toString());
        // get shopify orders
        OrderList orders = shopifyService.getOrdersBy("https://actimazo.myshopify.com/admin/api/2019-07/orders.json",
            "29e15279ee8564d0c333cba572a090d1", "76ea1ffedf641d9a3dfa6f0e93928bdf");
        if (orders != null && !orders.getOrders().isEmpty()) {
            System.out.println(orders.getOrders().size());
            // for each order find transaction
            orders.getOrders().forEach(order -> {
                String baseUrl = "https://actimazo.myshopify.com/admin/api/2019-07/orders/" + order.getId() + "/transactions.json";
                TransactionList transactions = shopifyService.getTransactions(baseUrl, "29e15279ee8564d0c333cba572a090d1",
                    "76ea1ffedf641d9a3dfa6f0e93928bdf");
                if (transactions != null && !transactions.getTransactions().isEmpty()) {
                    transactions.getTransactions().forEach(System.out::println);
                    TrackingDTO trackingDto = new TrackingDTO();
                    Instant now = Instant.now();
                    trackingDto.setCreated_at(now);
                    trackingDto.setUpdated_at(now);
                    trackingDto.setOrderId(order.getId());
                    trackingService.save(trackingDto);
                }
            });
        }
        // post to paypal
    }
}
