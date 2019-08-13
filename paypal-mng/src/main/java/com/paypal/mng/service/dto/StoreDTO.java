package com.paypal.mng.service.dto;
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
    private String shopify_api_key;

    @NotNull
    private String shopify_api_password;

    @NotNull
    private String store_name;

    private Instant created_at;

    private Instant updated_at;


    private Long paypalId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopify_api_key() {
        return shopify_api_key;
    }

    public void setShopify_api_key(String shopify_api_key) {
        this.shopify_api_key = shopify_api_key;
    }

    public String getShopify_api_password() {
        return shopify_api_password;
    }

    public void setShopify_api_password(String shopify_api_password) {
        this.shopify_api_password = shopify_api_password;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
    }

    public Long getPaypalId() {
        return paypalId;
    }

    public void setPaypalId(Long paypalId) {
        this.paypalId = paypalId;
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
            ", shopify_api_key='" + getShopify_api_key() + "'" +
            ", shopify_api_password='" + getShopify_api_password() + "'" +
            ", store_name='" + getStore_name() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            ", updated_at='" + getUpdated_at() + "'" +
            ", paypal=" + getPaypalId() +
            "}";
    }
}
