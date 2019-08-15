package com.paypal.mng.worker;

import com.paypal.mng.service.OrderService;
import com.paypal.mng.service.ShopifyService;
import com.paypal.mng.service.StoreService;
import com.paypal.mng.service.TrackingService;
import com.paypal.mng.service.dto.OrderDTO;
import com.paypal.mng.service.dto.TrackingDTO;
import com.paypal.mng.service.dto.shopify.Fulfillment;
import com.paypal.mng.service.dto.shopify.Order;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.TransactionList;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShopifyWorker {
    private final ShopifyService shopifyService;

    private final TrackingService trackingService;

    private final OrderService orderService;

    private final StoreService storeService;

    public ShopifyWorker(ShopifyService shopifyService, TrackingService trackingService, OrderService orderService, StoreService storeService) {
        this.shopifyService = shopifyService;
        this.trackingService = trackingService;
        this.orderService = orderService;
        this.storeService = storeService;
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public void process() {
        // get shopify orders
        storeService.findAll()
        OrderList orders = shopifyService.getOrdersBy("https://actimazo.myshopify.com/admin/api/2019-07/orders.json",
            "29e15279ee8564d0c333cba572a090d1", "76ea1ffedf641d9a3dfa6f0e93928bdf");
        if (orders != null && !orders.getOrders().isEmpty()) {
            System.out.println(orders.getOrders().size());
            // find order with order number already exist
            List<Integer> ordersExist = orderService.findByOrderNumbers(orders.getOrders().stream().map(Order::getOrderNumber).collect(Collectors.toList()))
                .stream().map(OrderDTO::getOrderNumber).collect(Collectors.toList());
            // for each order find transaction and created
            orders.getOrders().stream().filter(order -> ordersExist.contains(order.getOrderNumber()))
                .forEach(this::createOrder);
        }
        // post to paypal
    }

    @Transactional
    public void createOrder(Order order) {
        String baseUrl = "https://actimazo.myshopify.com/admin/api/2019-07/orders/" + order.getId() + "/transactions.json";
        TransactionList transactions = shopifyService.getTransactions(baseUrl, "29e15279ee8564d0c333cba572a090d1",
            "76ea1ffedf641d9a3dfa6f0e93928bdf");
        if (transactions != null && !transactions.getTransactions().isEmpty()) {
            transactions.getTransactions().forEach(System.out::println);
            Instant now = Instant.now();
            OrderDTO orderDto = new OrderDTO();
            orderDto.setCreatedAt(now);
            orderDto.setUpdatedAt(now);
            orderDto.setStoreId();
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
    }
}
