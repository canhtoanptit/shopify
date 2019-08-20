package com.paypal.mng.service.dto;
import com.paypal.mng.domain.Paypal;
import com.paypal.mng.domain.Store;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.paypal.mng.domain.Store} entity.
 */
public class StoreDTO implements Serializable {

    private Long id;

    @NotNull
    private String shopifyApiKey;

    @NotNull
    private String shopifyApiPassword;

    @NotNull
    private String storeName;

    private Instant createdAt;

    private Instant updatedAt;

    @NotNull
    private String shopifyApiUrl;

    private Boolean automationStatus;

    private Long sinceId;

    private Long paypalId;

    private Paypal paypal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopifyApiKey() {
        return shopifyApiKey;
    }

    public void setShopifyApiKey(String shopifyApiKey) {
        this.shopifyApiKey = shopifyApiKey;
    }

    public String getShopifyApiPassword() {
        return shopifyApiPassword;
    }

    public void setShopifyApiPassword(String shopifyApiPassword) {
        this.shopifyApiPassword = shopifyApiPassword;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getShopifyApiUrl() {
        return shopifyApiUrl;
    }

    public void setShopifyApiUrl(String shopifyApiUrl) {
        this.shopifyApiUrl = shopifyApiUrl;
    }

    public Boolean isAutomationStatus() {
        return automationStatus;
    }

    public void setAutomationStatus(Boolean automationStatus) {
        this.automationStatus = automationStatus;
    }

    public Long getSinceId() {
        return sinceId;
    }

    public void setSinceId(Long sinceId) {
        this.sinceId = sinceId;
    }

    public Long getPaypalId() {
        return paypalId;
    }

    public void setPaypalId(Long paypalId) {
        this.paypalId = paypalId;
    }

    public Paypal getPaypal() {
        return paypal;
    }

    public void setPaypal(Paypal paypal) {
        this.paypal = paypal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StoreDTO storeDTO = (StoreDTO) o;
        if (storeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StoreDTO{" +
            "id=" + getId() +
            ", shopifyApiKey='" + getShopifyApiKey() + "'" +
            ", shopifyApiPassword='" + getShopifyApiPassword() + "'" +
            ", storeName='" + getStoreName() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", shopifyApiUrl='" + getShopifyApiUrl() + "'" +
            ", automationStatus='" + isAutomationStatus() + "'" +
            ", sinceId=" + getSinceId() +
            ", paypal=" + getPaypalId() +
            "}";
    }

    public static StoreDTO toDto(Store store) {
        if ( store == null ) {
            return null;
        }

        StoreDTO storeDTO = new StoreDTO();

        storeDTO.setPaypalId( storePaypalId( store ) );
        storeDTO.setId( store.getId() );
        storeDTO.setShopifyApiKey( store.getShopifyApiKey() );
        storeDTO.setShopifyApiPassword( store.getShopifyApiPassword() );
        storeDTO.setStoreName( store.getStoreName() );
        storeDTO.setCreatedAt( store.getCreatedAt() );
        storeDTO.setUpdatedAt( store.getUpdatedAt() );
        storeDTO.setShopifyApiUrl( store.getShopifyApiUrl() );
        storeDTO.setAutomationStatus( store.isAutomationStatus() );
        storeDTO.setSinceId( store.getSinceId() );
        storeDTO.setPaypal(store.getPaypal());

        return storeDTO;
    }

    private static Long storePaypalId(Store store) {
        if ( store == null ) {
            return null;
        }
        Paypal paypal = store.getPaypal();
        if ( paypal == null ) {
            return null;
        }
        Long id = paypal.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
