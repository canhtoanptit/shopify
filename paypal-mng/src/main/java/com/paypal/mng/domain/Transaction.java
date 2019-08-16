package com.paypal.mng.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "authorization", nullable = false)
    private String authorization;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @NotNull
    @Column(name = "shopify_transaction_id", nullable = false, unique = true)
    private Long shopifyTransactionId;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("transactions")
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorization() {
        return authorization;
    }

    public Transaction authorization(String authorization) {
        this.authorization = authorization;
        return this;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Transaction createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Transaction updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getShopifyTransactionId() {
        return shopifyTransactionId;
    }

    public Transaction shopifyTransactionId(Long shopifyTransactionId) {
        this.shopifyTransactionId = shopifyTransactionId;
        return this;
    }

    public void setShopifyTransactionId(Long shopifyTransactionId) {
        this.shopifyTransactionId = shopifyTransactionId;
    }

    public Order getOrder() {
        return order;
    }

    public Transaction order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", authorization='" + getAuthorization() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", shopifyTransactionId=" + getShopifyTransactionId() +
            "}";
    }
}
