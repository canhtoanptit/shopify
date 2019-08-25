package com.paypal.mng.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.paypal.mng.domain.PaypalHistory} entity.
 */
public class PaypalHistoryDTO implements Serializable {

    private Long id;

    @NotNull
    private Long shopifyOrderId;

    @NotNull
    private String shopifyTrackingNumber;

    @NotNull
    private String shopifyAuthorizationKey;

    @NotNull
    private String shopifyShippingStatus;

    @NotNull
    private String carrier;

    @NotNull
    private Integer status;

    private Instant createdAt;

    private Instant updatedAt;

    @NotNull
    private Integer shopifyOrderNumber;

    private String shopifyOrderName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopifyOrderId() {
        return shopifyOrderId;
    }

    public void setShopifyOrderId(Long shopifyOrderId) {
        this.shopifyOrderId = shopifyOrderId;
    }

    public String getShopifyTrackingNumber() {
        return shopifyTrackingNumber;
    }

    public void setShopifyTrackingNumber(String shopifyTrackingNumber) {
        this.shopifyTrackingNumber = shopifyTrackingNumber;
    }

    public String getShopifyAuthorizationKey() {
        return shopifyAuthorizationKey;
    }

    public void setShopifyAuthorizationKey(String shopifyAuthorizationKey) {
        this.shopifyAuthorizationKey = shopifyAuthorizationKey;
    }

    public String getShopifyShippingStatus() {
        return shopifyShippingStatus;
    }

    public void setShopifyShippingStatus(String shopifyShippingStatus) {
        this.shopifyShippingStatus = shopifyShippingStatus;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getShopifyOrderNumber() {
        return shopifyOrderNumber;
    }

    public void setShopifyOrderNumber(Integer shopifyOrderNumber) {
        this.shopifyOrderNumber = shopifyOrderNumber;
    }

    public String getShopifyOrderName() {
        return shopifyOrderName;
    }

    public void setShopifyOrderName(String shopifyOrderName) {
        this.shopifyOrderName = shopifyOrderName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PaypalHistoryDTO paypalHistoryDTO = (PaypalHistoryDTO) o;
        if (paypalHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), paypalHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PaypalHistoryDTO{" +
            "id=" + getId() +
            ", shopifyOrderId=" + getShopifyOrderId() +
            ", shopifyTrackingNumber='" + getShopifyTrackingNumber() + "'" +
            ", shopifyAuthorizationKey='" + getShopifyAuthorizationKey() + "'" +
            ", shopifyShippingStatus='" + getShopifyShippingStatus() + "'" +
            ", carrier='" + getCarrier() + "'" +
            ", status=" + getStatus() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", shopifyOrderNumber=" + getShopifyOrderNumber() +
            ", shopifyOrderName='" + getShopifyOrderName() + "'" +
            "}";
    }
}
