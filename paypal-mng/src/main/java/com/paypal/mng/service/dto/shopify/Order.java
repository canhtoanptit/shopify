package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.paypal.mng.config.jackson.FlexDateDeserializer;
import com.paypal.mng.config.jackson.FlexDateSerializer;

import java.util.Date;
import java.util.List;

public class Order
{
    @JsonProperty(value = JsonConstants.ID)
    private long id;

    @JsonProperty(value = JsonConstants.NAME)
    private String name;

//    @JsonProperty(value = JsonConstants.TOTAL_PRICE)
//    private BigDecimal totalPrice;
//
//    @JsonProperty(value = JsonConstants.DISCOUNT)
//    private BigDecimal discount;
//
//    @JsonProperty(value = JsonConstants.FINANCIAL_STATUS)
//    private FinancialStatus financialStatus;
//
//    @JsonProperty(value = JsonConstants.CUSTOMER)
//    private Customer customer;

    @JsonProperty(value = JsonConstants.ORDER_NUMBER)
    private Integer orderNumber;

//    @JsonProperty(value = JsonConstants.BILLING_ADDRESS)
//    private Address billingAddress;
//
//    @JsonProperty(value = JsonConstants.SHIPPING_ADDRESS)
//    private Address shippingAddress;
//
//    @JsonProperty(value = JsonConstants.LINE_ITEMS)
//    private List<LineItem> lineItems;

    @JsonProperty(value = JsonConstants.CREATED_AT)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    @JsonSerialize(using = FlexDateSerializer.class)
    private Date createdAt;

    @JsonProperty(value = JsonConstants.PROCESSED_AT)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    @JsonSerialize(using = FlexDateSerializer.class)
    private Date processedAt;

    @JsonProperty(value = JsonConstants.FULFILLMENTS)
    private List<Fulfillment> fulfillments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Date processedAt) {
        this.processedAt = processedAt;
    }

    public List<Fulfillment> getFulfillments() {
        return fulfillments;
    }

    public void setFulfillments(List<Fulfillment> fulfillments) {
        this.fulfillments = fulfillments;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
