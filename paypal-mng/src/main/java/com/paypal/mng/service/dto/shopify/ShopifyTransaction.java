package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.paypal.mng.config.jackson.FlexDateDeserializer;
import com.paypal.mng.config.jackson.FlexDateSerializer;

import java.util.Date;

public class ShopifyTransaction {
    @JsonProperty(value = JsonConstants.ID)
    private long id;

    @JsonProperty(value = JsonConstants.ORDER_ID)
    private long orderId;

//    @JsonProperty(value = JsonConstants.AMOUNT)
//    private BigDecimal amount;
//
//    @JsonProperty(value = JsonConstants.KIND)
//    private String kind;

    @JsonProperty(value = JsonConstants.STATUS)
    private String status;

    @JsonProperty(value = JsonConstants.CREATED_AT)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    @JsonSerialize(using = FlexDateSerializer.class)
    private Date createdAt;

//    @JsonProperty(value = JsonConstants.CURRENCY)
//    private String currency;

    @JsonProperty(value = JsonConstants.AUTHORIZATION)
    private String authorization;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + id +
            ", orderId=" + orderId +
            ", status='" + status + '\'' +
            ", createdAt=" + createdAt +
            ", authorization='" + authorization + '\'' +
            '}';
    }
}
