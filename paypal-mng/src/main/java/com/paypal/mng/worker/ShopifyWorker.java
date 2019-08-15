package com.paypal.mng.worker;

import com.paypal.mng.service.OrderService;
import com.paypal.mng.service.ShopifyService;
import com.paypal.mng.service.TrackingService;
import com.paypal.mng.service.dto.OrderDTO;
import com.paypal.mng.service.dto.TrackingDTO;
import com.paypal.mng.service.dto.shopify.Fulfillment;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.TransactionList;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ShopifyWorker {
    private final ShopifyService shopifyService;

    private final TrackingService trackingService;

    private final OrderService orderService;

    public ShopifyWorker(ShopifyService shopifyService, TrackingService trackingService, OrderService orderService) {
        this.shopifyService = shopifyService;
        this.trackingService = trackingService;
        this.orderService = orderService;
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
                    Instant now = Instant.now();
                    OrderDTO orderDto = new OrderDTO();
                    orderDto.setCreatedAt(now);
                    orderDto.setUpdatedAt(now);
                    orderDto.setOrderNumber(order.getOrderNumber());
                    OrderDTO response = orderService.save(orderDto);
                    TrackingDTO trackingDto = new TrackingDTO();
                    trackingDto.setCreatedAt(now);
                    trackingDto.setUpdatedAt(now);
                    trackingDto.setOrderId(order.getId());
                    Fulfillment fulfillment = order.getFulfillments().get(0);
                    trackingDto.setTrackingNumber(fulfillment.getTrackingNumber());
                    trackingDto.setTrackingCompany(fulfillment.getTrackingCompany());
                    trackingDto.setTrackingUrl(fulfillment.getTrackingUrl());
                    trackingDto.setOrderId(response.getId());
                    trackingService.save(trackingDto);
                }
            });
        }
        // post to paypal
    }
}
