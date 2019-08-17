package com.paypal.mng.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Store.
 */
@Entity
@Table(name = "store")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "shopify_api_key", nullable = false)
    private String shopifyApiKey;

    @NotNull
    @Column(name = "shopify_api_password", nullable = false)
    private String shopifyApiPassword;

    @NotNull
    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @NotNull
    @Column(name = "shopify_api_url", nullable = false, unique = true)
    private String shopifyApiUrl;

    @Column(name = "automation_status")
    private Boolean automationStatus;

    @Column(name = "since_id")
    private Long sinceId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("stores")
    private Paypal paypal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopifyApiKey() {
        return shopifyApiKey;
    }

    public Store shopifyApiKey(String shopifyApiKey) {
        this.shopifyApiKey = shopifyApiKey;
        return this;
    }

    public void setShopifyApiKey(String shopifyApiKey) {
        this.shopifyApiKey = shopifyApiKey;
    }

    public String getShopifyApiPassword() {
        return shopifyApiPassword;
    }

    public Store shopifyApiPassword(String shopifyApiPassword) {
        this.shopifyApiPassword = shopifyApiPassword;
        return this;
    }

    public void setShopifyApiPassword(String shopifyApiPassword) {
        this.shopifyApiPassword = shopifyApiPassword;
    }

    public String getStoreName() {
        return storeName;
    }

    public Store storeName(String storeName) {
        this.storeName = storeName;
        return this;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Store createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Store updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getShopifyApiUrl() {
        return shopifyApiUrl;
    }

    public Store shopifyApiUrl(String shopifyApiUrl) {
        this.shopifyApiUrl = shopifyApiUrl;
        return this;
    }

    public void setShopifyApiUrl(String shopifyApiUrl) {
        this.shopifyApiUrl = shopifyApiUrl;
    }

    public Boolean isAutomationStatus() {
        return automationStatus;
    }

    public Store automationStatus(Boolean automationStatus) {
        this.automationStatus = automationStatus;
        return this;
    }

    public void setAutomationStatus(Boolean automationStatus) {
        this.automationStatus = automationStatus;
    }

    public Long getSinceId() {
        return sinceId;
    }

    public Store sinceId(Long sinceId) {
        this.sinceId = sinceId;
        return this;
    }

    public void setSinceId(Long sinceId) {
        this.sinceId = sinceId;
    }

    public Paypal getPaypal() {
        return paypal;
    }

    public Store paypal(Paypal paypal) {
        this.paypal = paypal;
        return this;
    }

    public void setPaypal(Paypal paypal) {
        this.paypal = paypal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Store)) {
            return false;
        }
        return id != null && id.equals(((Store) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Store{" +
            "id=" + getId() +
            ", shopifyApiKey='" + getShopifyApiKey() + "'" +
            ", shopifyApiPassword='" + getShopifyApiPassword() + "'" +
            ", storeName='" + getStoreName() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", shopifyApiUrl='" + getShopifyApiUrl() + "'" +
            ", automationStatus='" + isAutomationStatus() + "'" +
            ", sinceId=" + getSinceId() +
            "}";
    }
}
