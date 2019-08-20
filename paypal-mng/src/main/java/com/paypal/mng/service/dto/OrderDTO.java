package com.paypal.mng.service.dto;

import com.paypal.mng.domain.Order;
import com.paypal.mng.domain.Store;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.paypal.mng.domain.Order} entity.
 */
public class OrderDTO implements Serializable {

    private Long id;

    private Instant createdAt;

    private Instant updatedAt;

    @NotNull
    private Integer orderNumber;

    @NotNull
    private Long shopifyOrderId;

    private String orderName;

    private String storeName;

    private Long storeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getShopifyOrderId() {
        return shopifyOrderId;
    }

    public void setShopifyOrderId(Long shopifyOrderId) {
        this.shopifyOrderId = shopifyOrderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public static OrderDTO toDto(Order order) {
        if (order == null) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setStoreId(orderStoreId(order));
        orderDTO.setId(order.getId());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setUpdatedAt(order.getUpdatedAt());
        orderDTO.setOrderNumber(order.getOrderNumber());
        orderDTO.setShopifyOrderId(order.getShopifyOrderId());
        orderDTO.setOrderName(order.getOrderName());
        orderDTO.setStoreName(orderStoreName(order));

        return orderDTO;
    }

    private static Long orderStoreId(Order order) {
        if (order == null) {
            return null;
        }
        Store store = order.getStore();
        if (store == null) {
            return null;
        }
        Long id = store.getId();
        if (id == null) {
            return null;
        }
        return id;
    }

    private static String orderStoreName(Order order) {
        if (order == null) {
            return null;
        }
        Store store = order.getStore();
        if (store == null) {
            return null;
        }
        String storeName = store.getStoreName();
        if (storeName == null) {
            return null;
        }
        return storeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (orderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", orderNumber=" + getOrderNumber() +
            ", shopifyOrderId=" + getShopifyOrderId() +
            ", orderName='" + getOrderName() + "'" +
            ", store=" + getStoreId() +
            "}";
    }
}
