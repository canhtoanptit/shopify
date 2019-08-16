package com.paypal.mng.worker;

import com.paypal.mng.domain.Order;
import com.paypal.mng.service.*;
import com.paypal.mng.service.dto.*;
import com.paypal.mng.service.dto.shopify.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    private final TransactionService transactionService;

    private final PaypalHistoryService paypalHistoryService;

    public ShopifyWorker(ShopifyService shopifyService, TrackingService trackingService, OrderService orderService,
                         StoreService storeService, TransactionService transactionService, PaypalHistoryService paypalHistoryService) {
        this.shopifyService = shopifyService;
        this.trackingService = trackingService;
        this.orderService = orderService;
        this.storeService = storeService;
        this.transactionService = transactionService;
        this.paypalHistoryService = paypalHistoryService;
    }

    @Scheduled(cron = "0 */5 * * * ?")
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
                    orders.getOrders().forEach(shopifyOrder -> this.processShopifyOrder(storeDTO, shopifyOrder));
                }
            });
        }
    }

    private void processShopifyOrder(StoreDTO storeDTO, ShopifyOrder shopifyOrder) {
        String baseUrl = storeDTO.getShopifyApiUrl() + "orders/" + shopifyOrder.getId() + "/transactions.json";
        TransactionList transactions = shopifyService.getTransactions(baseUrl, storeDTO.getShopifyApiKey(), storeDTO.getShopifyApiPassword());
        if (transactions != null && !transactions.getTransactions().isEmpty()) {
            transactions.getTransactions().forEach(System.out::println);
            Optional<Order> existedOrder = orderService.findByOrderNumber(shopifyOrder.getOrderNumber());
            Long orderId;
            if (!existedOrder.isPresent()) {
                orderId = this.createOrder(storeDTO, shopifyOrder);
            } else {
                orderId = existedOrder.get().getId();
            }

            // created transactions
            createTransactions(transactions, orderId, shopifyOrder);

        } else {
            log.info("transaction is null or empty");
        }
    }

    private Long createOrder(StoreDTO storeDTO, ShopifyOrder order) {
        Instant now = Instant.now();
        OrderDTO orderDto = new OrderDTO();
        orderDto.setCreatedAt(now);
        orderDto.setUpdatedAt(now);
        orderDto.setStoreId(storeDTO.getId());
        orderDto.setShopifyOrderId(order.getId());
        orderDto.setOrderNumber(order.getOrderNumber());
        OrderDTO response = orderService.save(orderDto);
        return response.getId();
    }

    private void createTransactions(TransactionList transactions, Long orderId, ShopifyOrder shopifyOrder) {
        Instant now = Instant.now();
        transactions.getTransactions().forEach(shopifyTransaction -> {
            Optional<TransactionDTO> transOps = transactionService.findByShopifyTransactionIdAndOrderId(shopifyTransaction.getId(), orderId);
            if (!transOps.isPresent()) {
                TransactionDTO transDto = new TransactionDTO();
                transDto.setAuthorization(shopifyTransaction.getAuthorization());
                transDto.setOrderId(orderId);
                transDto.setShopifyTransactionId(shopifyTransaction.getId());
                transDto.setCreatedAt(now);
                transDto.setUpdatedAt(now);
                transactionService.save(transDto);
            }
            // created tracking
            List<Fulfillment> fulfillmentList = shopifyOrder.getFulfillments();
            if (fulfillmentList != null && !fulfillmentList.isEmpty()) {
                log.info("Size of fulfillment {} ", fulfillmentList.size());
                createTracking(fulfillmentList, orderId, shopifyTransaction, shopifyOrder.getId());
            }
        });
    }

    private void createTracking(List<Fulfillment> fulfillmentList, Long orderId, ShopifyTransaction shopifyTransaction, Long shopifyOrder) {
        Instant now = Instant.now();
        fulfillmentList.forEach(fulfillment -> {
            List<String> trackingNumbers = fulfillment.getTrackingNumbers();
            List<String> trackingUrls = fulfillment.getTrackingUrls();
            if (trackingNumbers != null) {
                log.info("Process fulfilment {}", fulfillment);
                for (int i = 0; i < trackingNumbers.size(); i++) {
                    TrackingDTO trackingDto = new TrackingDTO();
                    trackingDto.setCreatedAt(now);
                    trackingDto.setUpdatedAt(now);
                    String trackingNumber = trackingNumbers.get(i);
                    log.info("Process with tracking number {}", trackingNumber);
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
                    createPaypalHistory(trackingNumber, shopifyTransaction.getAuthorization(), "SHIPPED",
                        fulfillment.getTrackingCompany(), shopifyOrder);
                }
            }
        });
    }

    private void createPaypalHistory(String trackingNumber, String authorizationKey, String shippingStatus, String carrier, Long shopifyOrder) {
        Instant now = Instant.now();
        PaypalHistoryDTO ppHistoryDto = new PaypalHistoryDTO();
        ppHistoryDto.setShopifyOrderId(shopifyOrder);
        ppHistoryDto.setShopifyAuthorizationKey(authorizationKey);
        ppHistoryDto.setCarrier(carrier);
        ppHistoryDto.setShopifyShippingStatus(shippingStatus);
        ppHistoryDto.setShopifyTrackingNumber(trackingNumber);
        ppHistoryDto.setUpdatedAt(now);
        ppHistoryDto.setUpdatedAt(now);
        paypalHistoryService.save(ppHistoryDto);
    }
}
