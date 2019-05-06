package com.quyenbeo.shopifym.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Variants entity.
 */
public class VariantsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long productId;

    @NotNull
    private String title;

    @NotNull
    private String productTitle;

    private Double cost;

    @NotNull
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VariantsDTO variantsDTO = (VariantsDTO) o;
        if (variantsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), variantsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VariantsDTO{" +
            "id=" + getId() +
            ", productId=" + getProductId() +
            ", title='" + getTitle() + "'" +
            ", productTitle='" + getProductTitle() + "'" +
            ", cost=" + getCost() +
            ", price=" + getPrice() +
            "}";
    }
}
