package com.paypal.mng.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PaypalHistory.
 */
@Entity
@Table(name = "paypal_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PaypalHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "shopify_order_id", nullable = false)
    private Long shopifyOrderId;

    @NotNull
    @Column(name = "shopify_tracking_number", nullable = false)
    private String shopifyTrackingNumber;

    @NotNull
    @Column(name = "shopify_authorization_key", nullable = false)
    private String shopifyAuthorizationKey;

    @NotNull
    @Column(name = "shopify_shipping_status", nullable = false)
    private String shopifyShippingStatus;

    @NotNull
    @Column(name = "carrier", nullable = false)
    private String carrier;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @NotNull
    @Column(name = "shopify_order_number", nullable = false)
    private Integer shopifyOrderNumber;

    
    @Column(name = "shopify_order_name", unique = true)
    private String shopifyOrderName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopifyOrderId() {
        return shopifyOrderId;
    }

    public PaypalHistory shopifyOrderId(Long shopifyOrderId) {
        this.shopifyOrderId = shopifyOrderId;
        return this;
    }

    public void setShopifyOrderId(Long shopifyOrderId) {
        this.shopifyOrderId = shopifyOrderId;
    }

    public String getShopifyTrackingNumber() {
        return shopifyTrackingNumber;
    }

    public PaypalHistory shopifyTrackingNumber(String shopifyTrackingNumber) {
        this.shopifyTrackingNumber = shopifyTrackingNumber;
        return this;
    }

    public void setShopifyTrackingNumber(String shopifyTrackingNumber) {
        this.shopifyTrackingNumber = shopifyTrackingNumber;
    }

    public String getShopifyAuthorizationKey() {
        return shopifyAuthorizationKey;
    }

    public PaypalHistory shopifyAuthorizationKey(String shopifyAuthorizationKey) {
        this.shopifyAuthorizationKey = shopifyAuthorizationKey;
        return this;
    }

    public void setShopifyAuthorizationKey(String shopifyAuthorizationKey) {
        this.shopifyAuthorizationKey = shopifyAuthorizationKey;
    }

    public String getShopifyShippingStatus() {
        return shopifyShippingStatus;
    }

    public PaypalHistory shopifyShippingStatus(String shopifyShippingStatus) {
        this.shopifyShippingStatus = shopifyShippingStatus;
        return this;
    }

    public void setShopifyShippingStatus(String shopifyShippingStatus) {
        this.shopifyShippingStatus = shopifyShippingStatus;
    }

    public String getCarrier() {
        return carrier;
    }

    public PaypalHistory carrier(String carrier) {
        this.carrier = carrier;
        return this;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public Integer getStatus() {
        return status;
    }

    public PaypalHistory status(Integer status) {
        this.status = status;
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public PaypalHistory createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public PaypalHistory updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getShopifyOrderNumber() {
        return shopifyOrderNumber;
    }

    public PaypalHistory shopifyOrderNumber(Integer shopifyOrderNumber) {
        this.shopifyOrderNumber = shopifyOrderNumber;
        return this;
    }

    public void setShopifyOrderNumber(Integer shopifyOrderNumber) {
        this.shopifyOrderNumber = shopifyOrderNumber;
    }

    public String getShopifyOrderName() {
        return shopifyOrderName;
    }

    public PaypalHistory shopifyOrderName(String shopifyOrderName) {
        this.shopifyOrderName = shopifyOrderName;
        return this;
    }

    public void setShopifyOrderName(String shopifyOrderName) {
        this.shopifyOrderName = shopifyOrderName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaypalHistory)) {
            return false;
        }
        return id != null && id.equals(((PaypalHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PaypalHistory{" +
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
