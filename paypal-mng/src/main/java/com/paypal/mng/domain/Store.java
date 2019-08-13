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
    private String shopify_api_key;

    @NotNull
    @Column(name = "shopify_api_password", nullable = false)
    private String shopify_api_password;

    @NotNull
    @Column(name = "store_name", nullable = false)
    private String store_name;

    @Column(name = "created_at")
    private Instant created_at;

    @Column(name = "updated_at")
    private Instant updated_at;

    @ManyToOne
    private Paypal paypal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopify_api_key() {
        return shopify_api_key;
    }

    public Store shopify_api_key(String shopify_api_key) {
        this.shopify_api_key = shopify_api_key;
        return this;
    }

    public void setShopify_api_key(String shopify_api_key) {
        this.shopify_api_key = shopify_api_key;
    }

    public String getShopify_api_password() {
        return shopify_api_password;
    }

    public Store shopify_api_password(String shopify_api_password) {
        this.shopify_api_password = shopify_api_password;
        return this;
    }

    public void setShopify_api_password(String shopify_api_password) {
        this.shopify_api_password = shopify_api_password;
    }

    public String getStore_name() {
        return store_name;
    }

    public Store store_name(String store_name) {
        this.store_name = store_name;
        return this;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public Store created_at(Instant created_at) {
        this.created_at = created_at;
        return this;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public Store updated_at(Instant updated_at) {
        this.updated_at = updated_at;
        return this;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
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
            ", shopify_api_key='" + getShopify_api_key() + "'" +
            ", shopify_api_password='" + getShopify_api_password() + "'" +
            ", store_name='" + getStore_name() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            ", updated_at='" + getUpdated_at() + "'" +
            "}";
    }
}
