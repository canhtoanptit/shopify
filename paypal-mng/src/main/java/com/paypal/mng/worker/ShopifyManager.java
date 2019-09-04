package com.paypal.mng.worker;

import com.paypal.mng.service.ShopifyService;
import com.paypal.mng.service.StoreService;
import com.paypal.mng.service.dto.StoreDTO;
import com.paypal.mng.service.dto.shopify.OrderList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopifyManager {
    private final Logger log = LoggerFactory.getLogger(ShopifyManager.class);

    private final ShopifyService shopifyService;

    private final StoreService storeService;

    private final ShopifyWorker shopifyWorker;

    public ShopifyManager(ShopifyService shopifyService, StoreService storeService, ShopifyWorker shopifyWorker) {
        this.shopifyService = shopifyService;
        this.storeService = storeService;
        this.shopifyWorker = shopifyWorker;
    }

    @Scheduled(fixedDelay = 600000)
    public void processBatch() {
        List<StoreDTO> storeDTOS = storeService.findAllStore();
        if (!storeDTOS.isEmpty()) {
            storeDTOS.forEach(storeDTO -> {
                if (storeDTO.isAutomationStatus()) {
                    OrderList orders = shopifyService.getOrderExternalBatch(storeDTO);
                    if (orders != null && !orders.getOrders().isEmpty()) {
                        log.info("Process order with size {}", orders.getOrders().size());
                        // for each order find transaction and created
                        orders.getOrders().forEach(shopifyOrder -> this.shopifyWorker.processShopifyOrder(storeDTO, shopifyOrder));
                    }
                }
            });
        }
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void processRetry() {
        shopifyWorker.processRetry();
    }

    @Scheduled(fixedDelay = 7200000)
    public void processPartial() {
        List<StoreDTO> storeDTOS = storeService.findAllStore();
        if (!storeDTOS.isEmpty()) {
            storeDTOS.forEach(storeDTO -> {
                if (storeDTO.isAutomationStatus()) {
                    OrderList orders = shopifyService.getOrderPartialExternal(storeDTO);
                    if (orders != null && !orders.getOrders().isEmpty()) {
                        log.info("Process order with size {}", orders.getOrders().size());
                        // for each order find transaction and created
                        orders.getOrders().forEach(shopifyOrder -> this.shopifyWorker.processShopifyOrder(storeDTO, shopifyOrder));
                    }
                }
            });
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void process() {
        // get shopify orders
        List<StoreDTO> storeDTOS = storeService.findAllStore();
        if (!storeDTOS.isEmpty()) {
            storeDTOS.forEach(storeDTO -> {
                if (storeDTO.isAutomationStatus()) {
                    OrderList orders = shopifyService.getOrderExternal(storeDTO);
                    if (orders != null && !orders.getOrders().isEmpty()) {
                        log.info("Process order with size {}", orders.getOrders().size());
                        // for each order find transaction and created
                        orders.getOrders().forEach(shopifyOrder -> this.shopifyWorker.processShopifyOrder(storeDTO, shopifyOrder));
                    }
                }
            });
        }
    }
}
