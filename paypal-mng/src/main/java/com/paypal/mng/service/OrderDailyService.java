package com.paypal.mng.service;

import com.paypal.mng.service.dto.OrderDailyDTO;
import com.paypal.mng.service.dto.shopify.LineItem;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.dto.shopify.ShopifyOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDailyService {
    private final Logger logger = LoggerFactory.getLogger(OrderDailyService.class);

    private final ShopifyService shopifyService;

    public OrderDailyService(ShopifyService shopifyService) {
        this.shopifyService = shopifyService;
    }

    public List<OrderDailyDTO> findAll() {
        logger.info("Start daily logging");
        OrderList allOrders = shopifyService.getOrderDaily();
        logger.info("Size of orders {}", allOrders.getOrders().size());
        return allOrders.getOrders().stream()
            .map(this::getFromShopifyOrder).collect(Collectors.toList());
    }

    public Optional<OrderDailyDTO> findById(Long id) {
        return null;
    }

    private OrderDailyDTO getFromShopifyOrder(ShopifyOrder shopifyOrder) {
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
        StringBuilder lineItems = new StringBuilder();
        int quantity = 0;
        List<LineItem> itemList = shopifyOrder.getLineItems();
        if (itemList != null) {
            for(LineItem lineItem : itemList) {
                lineItems.append(lineItem.getName()).append(" ,");
                quantity += quantity + lineItem.getQuantity();
            }
        }
        dailyDTO.setLineItemName(lineItems.toString());
        dailyDTO.setLineItemQuantity(quantity);
        return dailyDTO;
    }

}
