package com.paypal.mng.service.dto.csv;

import com.opencsv.bean.CsvBindByName;

public class TrackingManual {
    @CsvBindByName(column = "ORDER_ID")
    private String orderId;

    @CsvBindByName(column = "TRACKING_NUMBER")
    private String trackingNumber;

    @CsvBindByName(column = "STORE_NAME")
    private String storeName;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
