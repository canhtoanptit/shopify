package com.paypal.mng.worker;

import com.paypal.mng.service.OrderService;
import com.paypal.mng.service.ShopifyService;
import com.paypal.mng.service.StoreService;
import com.paypal.mng.service.TrackingService;
import com.paypal.mng.service.dto.OrderDTO;
import com.paypal.mng.service.dto.StoreDTO;
import com.paypal.mng.service.dto.TrackingDTO;
import com.paypal.mng.service.dto.shopify.Fulfillment;
import com.paypal.mng.service.dto.shopify.Order;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.TransactionList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class ShopifyWorker {
    private final Logger log = LoggerFactory.getLogger(ShopifyWorker.class);
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
        List<StoreDTO> storeDTOS = storeService.findAllStore();
        if (!storeDTOS.isEmpty()) {
            storeDTOS.forEach(storeDTO -> {
                OrderList orders = shopifyService.getOrdersBy(storeDTO.getShopifyApiUrl() + "orders.json",
                    storeDTO.getShopifyApiKey(), storeDTO.getShopifyApiPassword());
                if (orders != null && !orders.getOrders().isEmpty()) {
                    log.info("Process order with size {}", orders.getOrders().size());
                    // for each order find transaction and created
                    orders.getOrders().forEach(order -> this.createOrder(storeDTO, order));
                }
            });
        }
        // post to paypal
    }

    @Transactional
    public void createOrder(StoreDTO storeDTO, Order order) {
        String baseUrl = storeDTO.getShopifyApiUrl() + "orders/" +order.getId() + "/transactions.json";
        TransactionList transactions = shopifyService.getTransactions(baseUrl, storeDTO.getShopifyApiKey(), storeDTO.getShopifyApiPassword());
        if (transactions != null && !transactions.getTransactions().isEmpty()) {
            transactions.getTransactions().forEach(System.out::println);
            Instant now = Instant.now();
            Optional<com.paypal.mng.domain.Order> existedOrder = orderService.findByOrderNumber(order.getOrderNumber());
            Long orderId;
            if (!existedOrder.isPresent()) {
                OrderDTO orderDto = new OrderDTO();
                orderDto.setCreatedAt(now);
                orderDto.setUpdatedAt(now);
                orderDto.setStoreId(storeDTO.getId());
                orderDto.setOrderNumber(order.getOrderNumber());
                OrderDTO response = orderService.save(orderDto);
                orderId = response.getId();
            } else {
                orderId = existedOrder.get().getId();
            }
            List<Fulfillment> fulfillmentList = order.getFulfillments();
            if (fulfillmentList != null && !fulfillmentList.isEmpty()) {
                log.info("Size of fulfillment {} ", fulfillmentList.size());
                fulfillmentList.forEach(fulfillment -> {
                    List<String> trackingNumbers = fulfillment.getTrackingNumbers();
                    List<String> trackingUrls = fulfillment.getTrackingUrls();
                    if (trackingNumbers != null) {
                        for (int i = 0; i < trackingNumbers.size(); i++) {
                            TrackingDTO trackingDto = new TrackingDTO();
                            trackingDto.setCreatedAt(now);
                            trackingDto.setUpdatedAt(now);
                            trackingDto.setOrderId(order.getId());
                            trackingDto.setTrackingNumber(trackingNumbers.get(i));
                            trackingDto.setTrackingCompany(fulfillment.getTrackingCompany());
                            if (trackingUrls != null && trackingUrls.size() == trackingNumbers.size()) {
                                trackingDto.setTrackingUrl(fulfillment.getTrackingUrls().get(i));
                            } else if (trackingUrls != null) {
                                log.info("total tracking url difference than total tracking number: {} {}", trackingUrls.size(), trackingNumbers.size());
                            } else {
                                log.info("Tracking url null");
                            }
                            trackingDto.setOrderId(orderId);
                            trackingService.save(trackingDto);
                        }
                    }
                });
            }
        }
    }
}
