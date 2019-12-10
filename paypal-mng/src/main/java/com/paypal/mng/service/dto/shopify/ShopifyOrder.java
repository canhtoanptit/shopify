package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.paypal.mng.config.jackson.FlexDateDeserializer;
import com.paypal.mng.config.jackson.FlexDateSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ShopifyOrder
{
    @JsonProperty(value = JsonConstants.ID)
    private long id;

    @JsonProperty(value = JsonConstants.NAME)
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty(value = JsonConstants.TOTAL_PRICE)
    private BigDecimal totalPrice;

    @JsonProperty(value = JsonConstants.DISCOUNT)
    private BigDecimal discount;

    @JsonProperty(value = JsonConstants.FINANCIAL_STATUS)
    private FinancialStatus financialStatus;

    @JsonProperty(value = JsonConstants.CUSTOMER)
    private Customer customer;

    @JsonProperty(value = JsonConstants.ORDER_NUMBER)
    private Integer orderNumber;

    @JsonProperty(value = JsonConstants.BILLING_ADDRESS)
    private Address billingAddress;

    @JsonProperty(value = JsonConstants.SHIPPING_ADDRESS)
    private Address shippingAddress;

    @JsonProperty(value = JsonConstants.LINE_ITEMS)
    private List<LineItem> lineItems;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public FinancialStatus getFinancialStatus() {
        return financialStatus;
    }

    public void setFinancialStatus(FinancialStatus financialStatus) {
        this.financialStatus = financialStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopifyOrder that = (ShopifyOrder) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ShopifyOrder{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", totalPrice=" + totalPrice +
            ", discount=" + discount +
            ", financialStatus=" + financialStatus +
            ", customer=" + customer +
            ", orderNumber=" + orderNumber +
            ", billingAddress=" + billingAddress +
            ", shippingAddress=" + shippingAddress +
            ", lineItems=" + lineItems +
            ", createdAt=" + createdAt +
            ", processedAt=" + processedAt +
            ", fulfillments=" + fulfillments +
            '}';
    }
}
