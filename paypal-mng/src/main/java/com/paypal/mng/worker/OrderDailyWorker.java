package com.paypal.mng.worker;

import com.paypal.mng.service.FileService;
import com.paypal.mng.service.OrderDailyService;
import com.paypal.mng.service.dto.OrderDailyDTO;
import com.paypal.mng.service.dto.shopify.LineItem;
import com.paypal.mng.service.dto.shopify.ShopifyOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDailyWorker {

    private final Logger logger = LoggerFactory.getLogger(OrderDailyWorker.class);

    private final OrderDailyCacheManager orderDailyCacheManager;

    private final OrderDailyService orderDailyService;

    private final FileService fileService;

    public OrderDailyWorker(OrderDailyCacheManager orderDailyCacheManager, OrderDailyService orderDailyService,
                            FileService fileService) {
        this.orderDailyCacheManager = orderDailyCacheManager;
        this.orderDailyService = orderDailyService;
        this.fileService = fileService;
    }

    @Scheduled(cron = "0 10 */2 * * ?")
    void getOrderDaily() {
        try {
            orderDailyService.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Scheduled(cron = "0 40 23 * * ?")
    void saveOrderDailyToFile() {
        String filePath = null;
        try {
            List<OrderDailyDTO> data = new ArrayList<>();
            orderDailyCacheManager.getAllOrder()
                .forEach(shopifyOrder -> data.addAll(this.getFromShopifyOrder(shopifyOrder)));
            filePath = fileService.writeOrderDaily(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderDailyCacheManager.clearCache();
        }
        logger.info("Write file to {}", filePath);
    }

    private List<OrderDailyDTO> getFromShopifyOrder(ShopifyOrder shopifyOrder) {
        List<OrderDailyDTO> orderDailyDTOS = new ArrayList<>();
        List<LineItem> itemList = shopifyOrder.getLineItems();
        if (itemList != null && !itemList.isEmpty()) {
            itemList.forEach(lineItem -> {
                OrderDailyDTO order = createCommonField(shopifyOrder);
                order.setLineItemName(lineItem.getName());
                order.setLineItemQuantity(lineItem.getQuantity());
                orderDailyDTOS.add(order);
            });
        } else {
            orderDailyDTOS.add(createCommonField(shopifyOrder));
        }
        return orderDailyDTOS;
    }

    private OrderDailyDTO createCommonField(ShopifyOrder shopifyOrder) {
        OrderDailyDTO dailyDTO = new OrderDailyDTO();
        dailyDTO.setEmail(shopifyOrder.getEmail());
        dailyDTO.setFinancialStatus(shopifyOrder.getFinancialStatus().toString());
        dailyDTO.setName(shopifyOrder.getName());
        dailyDTO.setShipingAddress(shopifyOrder.getShippingAddress().getAddress1());
        dailyDTO.setShipingAddress2(shopifyOrder.getShippingAddress().getAddress2());
        dailyDTO.setShipingCity(shopifyOrder.getShippingAddress().getCity());
        dailyDTO.setShipingCompany(shopifyOrder.getShippingAddress().getCompany());
        dailyDTO.setShipingCountry(shopifyOrder.getShippingAddress().getCountry());
        dailyDTO.setShipingPhone(shopifyOrder.getShippingAddress().getPhone());
        dailyDTO.setShipingStreet(shopifyOrder.getShippingAddress().getAddress1());
        dailyDTO.setShipingProvince(shopifyOrder.getShippingAddress().getProvince());
        dailyDTO.setShipingZip(shopifyOrder.getShippingAddress().getZip());
        dailyDTO.setShipingName(shopifyOrder.getShippingAddress().getFirstName() + " "
            + shopifyOrder.getShippingAddress().getLastName());
        dailyDTO.setId(shopifyOrder.getId());
        dailyDTO.setPaidAt(shopifyOrder.getProcessedAt());
        return dailyDTO;
    }
}
