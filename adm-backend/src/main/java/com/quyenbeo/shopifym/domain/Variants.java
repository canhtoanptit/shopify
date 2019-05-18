package com.quyenbeo.shopifym.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Variants.
 */
@Entity
@Table(name = "variants")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Variants implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "product_title", nullable = false)
    private String productTitle;

    @Column(name = "jhi_cost")
    private Double cost;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "mo")
    private Double mo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public Variants productId(Long productId) {
        this.productId = productId;
        return this;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public Variants title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public Variants productTitle(String productTitle) {
        this.productTitle = productTitle;
        return this;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Double getCost() {
        return cost;
    }

    public Variants cost(Double cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPrice() {
        return price;
    }

    public Variants price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMo() {
        return mo;
    }

    public Variants mo(Double mo) {
        this.mo = mo;
        return this;
    }

    public void setMo(Double mo) {
        this.mo = mo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Variants variants = (Variants) o;
        if (variants.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), variants.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Variants{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", title='" + getTitle() + "'" +
            ", productTitle='" + getProductTitle() + "'" +
            ", cost=" + getCost() +
            ", price=" + getPrice() +
            ", mo=" + getMo() +
            "}";
    }
}
