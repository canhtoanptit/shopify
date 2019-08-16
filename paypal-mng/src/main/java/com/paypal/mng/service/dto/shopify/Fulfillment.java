package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Fulfillment {
    @JsonProperty(value = JsonConstants.ID)
    private long id;

    @JsonProperty(value = "order_id")
    private long orderId;

    @JsonProperty(value = "tracking_company")
    private String trackingCompany;

    @JsonProperty(value = "tracking_number")
    private String trackingNumber;

    @JsonProperty(value = "tracking_numbers")
    private List<String> trackingNumbers;

    @JsonProperty(value = "tracking_url")
    private String trackingUrl;

    @JsonProperty(value = "tracking_urls")
    private List<String> trackingUrls;

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

    public String getTrackingCompany() {
        return trackingCompany;
    }

    public void setTrackingCompany(String trackingCompany) {
        this.trackingCompany = trackingCompany;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public List<String> getTrackingNumbers() {
        return trackingNumbers;
    }

    public void setTrackingNumbers(List<String> trackingNumbers) {
        this.trackingNumbers = trackingNumbers;
    }

    public List<String> getTrackingUrls() {
        return trackingUrls;
    }

    public void setTrackingUrls(List<String> trackingUrls) {
        this.trackingUrls = trackingUrls;
    }

    @Override
    public String toString() {
        return "Fulfillment{" +
            "id=" + id +
            ", orderId=" + orderId +
            ", trackingCompany='" + trackingCompany + '\'' +
            ", trackingNumber='" + trackingNumber + '\'' +
            ", trackingNumbers=" + trackingNumbers +
            ", trackingUrl='" + trackingUrl + '\'' +
            ", trackingUrls=" + trackingUrls +
            '}';
    }
}
