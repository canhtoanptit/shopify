package com.paypal.mng.worker;

import com.paypal.mng.config.Constants;
import com.paypal.mng.domain.Order;
import com.paypal.mng.repository.RedisCacheRepo;
import com.paypal.mng.service.*;
import com.paypal.mng.service.dto.*;
import com.paypal.mng.service.dto.paypal.TokenDTO;
import com.paypal.mng.service.dto.paypal.Tracker;
import com.paypal.mng.service.dto.paypal.TrackerIdentifierListDTO;
import com.paypal.mng.service.dto.paypal.TrackerList;
import com.paypal.mng.service.dto.shopify.Fulfillment;
import com.paypal.mng.service.dto.shopify.ShopifyOrder;
import com.paypal.mng.service.dto.shopify.ShopifyTransaction;
import com.paypal.mng.service.dto.shopify.TransactionList;
import com.paypal.mng.service.external.PaypalApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ShopifyWorker {
    private final Logger log = LoggerFactory.getLogger(ShopifyWorker.class);

    private final TrackingService trackingService;

    private final OrderService orderService;

    private final TransactionService transactionService;

    private final PaypalHistoryService paypalHistoryService;

    private final PaypalApiClient paypalApiClient;

    private final RedisCacheRepo redisCacheRepo;

    private final ShopifyService shopifyService;

    public ShopifyWorker(TrackingService trackingService, OrderService orderService, TransactionService transactionService,
                         PaypalHistoryService paypalHistoryService, PaypalApiClient paypalApiClient,
                         RedisCacheRepo redisCacheRepo, ShopifyService shopifyService) {
        this.trackingService = trackingService;
        this.orderService = orderService;
        this.transactionService = transactionService;
        this.paypalHistoryService = paypalHistoryService;
        this.paypalApiClient = paypalApiClient;
        this.redisCacheRepo = redisCacheRepo;
        this.shopifyService = shopifyService;
    }

    public void processShopifyOrder(StoreDTO storeDTO, ShopifyOrder shopifyOrder) throws InterruptedException {
        log.debug("Process store {} and order {}", storeDTO, shopifyOrder);
        Optional<Order> existedOrder = orderService.findByOrderName(shopifyOrder.getName());
        Long orderId;
        if (!existedOrder.isPresent()) {
            orderId = this.createOrder(storeDTO, shopifyOrder);
        } else {
            orderId = existedOrder.get().getId();
        }
        String baseUrl = storeDTO.getShopifyApiUrl() + "orders/" + shopifyOrder.getId() + "/transactions.json";
        TransactionList transactions = shopifyService.getTransactions(baseUrl, storeDTO.getShopifyApiKey(), storeDTO.getShopifyApiPassword());
        if (transactions != null && transactions.getTransactions().size() == 1) {
            log.debug("Transaction with size: {}", transactions.getTransactions().size());
            // created transactions
            createTransactions(transactions, orderId, shopifyOrder, storeDTO);

        } else {
            log.debug("transaction is null or size is not 1");
            if (transactions != null && transactions.getTransactions() != null) {
                List<ShopifyTransaction> rs = new ArrayList<>();
                for (ShopifyTransaction shopifyTransaction : transactions.getTransactions()) {
                    if (shopifyTransaction.getStatus().equals(Constants.TRANSACTION_STATUS_SUCCESS)) {
                        rs.add(shopifyTransaction);
                    }
                }
                transactions.setTransactions(rs);
                createTransactions(transactions, orderId, shopifyOrder, storeDTO);
            }
        }
        Thread.sleep(300);
    }

    public void processRetry() {
        List<PaypalHistoryDTO> listOrderProcessFail = paypalHistoryService.findAllHistoryUploadFail();
        listOrderProcessFail.forEach(this::accept);
    }

    private Long createOrder(StoreDTO storeDTO, ShopifyOrder order) {
        OrderDTO orderDto = new OrderDTO();
        Instant now = Instant.now();
        orderDto.setCreatedAt(now);
        orderDto.setUpdatedAt(now);
        orderDto.setStoreId(storeDTO.getId());
        orderDto.setShopifyOrderId(order.getId());
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setOrderName(order.getName());
        OrderDTO response = orderService.save(orderDto);
        return response.getId();
    }

    private void createTransactions(TransactionList transactions, Long orderId, ShopifyOrder shopifyOrder, StoreDTO storeDTO) {
        Instant now = Instant.now();
        transactions.getTransactions().forEach(shopifyTransaction -> {
            try {
                log.debug("Process transaction {}", shopifyTransaction);
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
                    List<Tracker> trackers = createTracking(fulfillmentList, orderId, shopifyTransaction, shopifyOrder.getId(),
                        shopifyOrder.getOrderNumber(), shopifyOrder.getName());
                    addTrackingToPaypal(trackers, storeDTO);
                }
            } catch (Exception ex) {
                log.error("Error when createTransactions ", ex);
            }
        });
    }

    private void addTrackingToPaypal(List<Tracker> trackers, StoreDTO storeDTO) {
        if (trackers != null && !trackers.isEmpty()) {
            TrackerList trackerList = new TrackerList();
            trackerList.setTrackerList(trackers);
            String token = redisCacheRepo.getValue(storeDTO.getPaypalId().toString());
            if (token == null || token.isEmpty()) {
                TokenDTO tokenDto = paypalApiClient.getToken(storeDTO.getPaypal().getClientId(), storeDTO.getPaypal().getSecret());
                if (tokenDto.getAccessToken() != null && tokenDto.getExpriresIn() > 0) {
                    redisCacheRepo.setValue(storeDTO.getPaypalId().toString(), tokenDto.getAccessToken(), Duration.ofSeconds(tokenDto.getExpriresIn()));
                    token = tokenDto.getAccessToken();
                }
            }
            TrackerIdentifierListDTO res = paypalApiClient
                .addTrackersBatch(token, trackerList);
            if (res != null && res.getTrackerList() != null) {
                res.getTrackerList().forEach(tracker -> {
                    try {
                        List<PaypalHistoryDTO> history = paypalHistoryService.findByTransactionIdAndTrackingNumber(tracker.getTransactionId(),
                            tracker.getTrackingNumber());
                        if (history != null && !history.isEmpty()) {
                            history.get(0).setStatus(Constants.PAYPAL_ADD_TRACKING_SUCCESS);
                            paypalHistoryService.save(history.get(0));
                        }
                    } catch (Exception ex) {
                        log.error("Error when addTrackingToPaypal ", ex);
                    }
                });
            }
        }
    }

    private List<Tracker> createTracking(List<Fulfillment> fulfillmentList, Long orderId,
                                         ShopifyTransaction shopifyTransaction, Long shopifyOrderId, Integer orderNumber, String orderName) {
        Instant now = Instant.now();
        ArrayList<Tracker> trackers = new ArrayList<>();
        for (Fulfillment fulfillment : fulfillmentList) {
            try {
                List<String> trackingNumbers = fulfillment.getTrackingNumbers();
                List<String> trackingUrls = fulfillment.getTrackingUrls();
                if (trackingNumbers != null) {
                    log.debug("Process fulfilment {}", fulfillment);
                    for (int i = 0; i < trackingNumbers.size(); i++) {
                        try {
                            String trackingNumber = trackingNumbers.get(i);
                            Optional<TrackingDTO> trackOpt = trackingService.findByTrackingNumber(trackingNumber);
                            if (!trackOpt.isPresent()) {
                                TrackingDTO trackingDto = new TrackingDTO();
                                trackingDto.setCreatedAt(now);
                                trackingDto.setUpdatedAt(now);
                                log.debug("Process with tracking number {}", trackingNumber);
                                trackingDto.setTrackingNumber(trackingNumbers.get(i));
                                trackingDto.setTrackingCompany(fulfillment.getTrackingCompany());
                                if (trackingUrls != null && trackingUrls.size() == trackingNumbers.size()) {
                                    trackingDto.setTrackingUrl(fulfillment.getTrackingUrls().get(i));
                                } else if (trackingUrls != null) {
                                    log.debug("total tracking url difference than total tracking number: {} {}", trackingUrls.size(), trackingNumbers.size());
                                } else {
                                    log.debug("Tracking url null");
                                }
                                trackingDto.setOrderId(orderId);
                                trackingService.save(trackingDto);
                            }
                            if ("Other".equals(fulfillment.getTrackingCompany()) || "China Post".equals(fulfillment.getTrackingCompany())) {
                                fulfillment.setTrackingCompany("CHINA_POST");
                            }
                            List<PaypalHistoryDTO> hisOpt = paypalHistoryService.findByTransactionIdAndTrackingNumber(shopifyTransaction.getAuthorization(),
                                trackingNumber);
                            if (hisOpt == null || hisOpt.isEmpty()) {
                                createPaypalHistory(trackingNumber, shopifyTransaction.getAuthorization(), "SHIPPED",
                                    fulfillment.getTrackingCompany(), shopifyOrderId, orderNumber, orderName);
                            } else if (hisOpt.get(0).getStatus() == Constants.CALLED) {
                                Tracker tracker = new Tracker();
                                tracker.setStatus("SHIPPED");
                                tracker.setCarrier(fulfillment.getTrackingCompany());
                                tracker.setTrackingNumber(trackingNumber);
                                tracker.setTransactionId(shopifyTransaction.getAuthorization());
                                trackers.add(tracker);
                            }
                        } catch (Exception ex) {
                            log.error("Error when createTracking ", ex);
                        }
                    }
                }
            } catch (Exception ex) {
                log.error("Error when createTracking ", ex);
            }
        }
        return trackers;
    }

    private void createPaypalHistory(String trackingNumber, String authorizationKey, String shippingStatus,
                                     String carrier, Long shopifyOrder, Integer orderNumber, String orderName) {
        Instant now = Instant.now();
        PaypalHistoryDTO ppHistoryDto = new PaypalHistoryDTO();
        ppHistoryDto.setShopifyOrderId(shopifyOrder);
        ppHistoryDto.setShopifyAuthorizationKey(authorizationKey);
        ppHistoryDto.setCarrier(carrier);
        ppHistoryDto.setShopifyShippingStatus(shippingStatus);
        ppHistoryDto.setShopifyTrackingNumber(trackingNumber);
        ppHistoryDto.setUpdatedAt(now);
        ppHistoryDto.setCreatedAt(now);
        ppHistoryDto.setStatus(Constants.CALLED);
        ppHistoryDto.setShopifyOrderNumber(orderNumber);
        ppHistoryDto.setShopifyOrderName(orderName);
        paypalHistoryService.save(ppHistoryDto);
    }

    private void accept(PaypalHistoryDTO paypalHistoryDTO) {
        try {
            String numberProcessed = redisCacheRepo.getValue(paypalHistoryDTO.getShopifyOrderName());
            if (numberProcessed == null || !numberProcessed.equals("4")) {
                Optional<Order> storeDtoOpt = orderService.findByShopifyOrderId(paypalHistoryDTO.getShopifyOrderId());
                if (storeDtoOpt.isPresent() && storeDtoOpt.get().getStore() != null) {
                    Tracker tracker = new Tracker();
                    tracker.setCarrier(paypalHistoryDTO.getCarrier());
                    tracker.setStatus(paypalHistoryDTO.getShopifyShippingStatus());
                    tracker.setTrackingNumber(paypalHistoryDTO.getShopifyTrackingNumber());
                    tracker.setTransactionId(paypalHistoryDTO.getShopifyAuthorizationKey());
                    StoreDTO storeDTO = new StoreDTO();
                    storeDTO.setPaypalId(storeDtoOpt.get().getStore().getPaypal().getId());
                    storeDTO.setPaypal(storeDtoOpt.get().getStore().getPaypal());
                    storeDTO.setShopifyApiUrl(storeDtoOpt.get().getStore().getShopifyApiUrl());
                    log.info("Store in processRetry {} ", storeDTO);
                    addTrackingToPaypal(Collections.singletonList(tracker), storeDTO);
                    if (numberProcessed == null) {
                        numberProcessed = "1";
                    } else {
                        numberProcessed = Integer.toString(Integer.parseInt(numberProcessed) + 1);
                    }
                    redisCacheRepo.setValue(paypalHistoryDTO.getShopifyOrderName(), numberProcessed);
                }
            }
        } catch (Exception ex) {
            log.error("Exception when retry process ", ex);
        }
    }
}
