package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class LineItem
{
    @JsonProperty(value = JsonConstants.ID)
    private long id;

    @JsonProperty(value = JsonConstants.PRODUCT_ID)
    private long productId;

    @JsonProperty(value = JsonConstants.PRODUCT_EXISTS)
    private boolean productExists;

    @JsonProperty(value = JsonConstants.VARIANT_ID)
    private long variantId;

    @JsonProperty(value = JsonConstants.TITLE)
    private String title;

    @JsonProperty(value = JsonConstants.NAME)
    private String name;

    @JsonProperty(value = JsonConstants.VARIANT_TITLE)
    private String variantTitle;

    @JsonProperty(value = JsonConstants.QUANTITY)
    private int quantity;

    @JsonProperty(value = JsonConstants.PRICE)
    private BigDecimal price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public boolean isProductExists() {
        return productExists;
    }

    public void setProductExists(boolean productExists) {
        this.productExists = productExists;
    }

    public long getVariantId() {
        return variantId;
    }

    public void setVariantId(long variantId) {
        this.variantId = variantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariantTitle() {
        return variantTitle;
    }

    public void setVariantTitle(String variantTitle) {
        this.variantTitle = variantTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "LineItem{" +
            "id=" + id +
            ", productId=" + productId +
            ", productExists=" + productExists +
            ", variantId=" + variantId +
            ", title='" + title + '\'' +
            ", name='" + name + '\'' +
            ", variantTitle='" + variantTitle + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            '}';
    }
}
