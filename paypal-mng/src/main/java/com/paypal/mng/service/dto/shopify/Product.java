package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.paypal.mng.config.jackson.FlexDateDeserializer;
import com.paypal.mng.config.jackson.FlexDateSerializer;

import java.util.Date;
import java.util.List;

public class Product
{
    @JsonProperty(value = JsonConstants.ID)
    private long id;

    @JsonProperty(value = JsonConstants.TITLE)
    private String title;

    @JsonProperty(value = JsonConstants.HANDLE)
    private String handle;

    @JsonProperty(value = JsonConstants.IMAGE)
    private Image featuredImage;

    @JsonProperty(value = JsonConstants.IMAGES)
    private List<Image> images;

    @JsonProperty(value = JsonConstants.PUBLISHED_AT)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    @JsonSerialize(using = FlexDateSerializer.class)
    private Date publishedAt;

    @JsonProperty(value = JsonConstants.PRODUCT_VARIANTS)
    private List<ProductVariant> productVariants;

    @JsonProperty(value = JsonConstants.PRODUCT_VARIANT_OPTIONS)
    private List<VariantOption> variantOptions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Image getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(Image featuredImage) {
        this.featuredImage = featuredImage;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public List<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(List<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }

    public List<VariantOption> getVariantOptions() {
        return variantOptions;
    }

    public void setVariantOptions(List<VariantOption> variantOptions) {
        this.variantOptions = variantOptions;
    }
}
