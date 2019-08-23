package com.paypal.mng.service.dto.csv;

import com.opencsv.bean.CsvBindByName;
import com.paypal.mng.config.Constants;

public class TrackingReport {

    @CsvBindByName(column = "ORDER_ID")
    private String orderName;

    @CsvBindByName(column = "TRACKING_NUMBER")
    private String trackingNumber;

    @CsvBindByName(column = "TRACKING_COMPANY")
    private String trackingCompany;

    @CsvBindByName(column = "TRACKING_URL")
    private String trackingUrl;

    @CsvBindByName(column = "STATUS")
    private String status = Constants.ORDER_NOT_PROCESS;

    @CsvBindByName(column = "STATUS_PAYPAL_API_CALL")
    private String uploadPaypalStatus;

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingCompany() {
        return trackingCompany;
    }

    public void setTrackingCompany(String trackingCompany) {
        this.trackingCompany = trackingCompany;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUploadPaypalStatus() {
        return uploadPaypalStatus;
    }

    public void setUploadPaypalStatus(String uploadPaypalStatus) {
        this.uploadPaypalStatus = uploadPaypalStatus;
    }
}
