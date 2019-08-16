package com.paypal.mng.service.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tracker {
    @JsonProperty("transaction_id")
    private String transactionId;

    @JsonProperty("tracking_number")
    private String trackingNumber;

    @JsonProperty("status")
    private String status;

    @JsonProperty("carrier")
    private String carrier;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public Tracker() {
    }

    @Override
    public String toString() {
        return "Tracker{" +
            "transactionId='" + transactionId + '\'' +
            ", trackingNumber=" + trackingNumber +
            ", status='" + status + '\'' +
            ", carrier='" + carrier + '\'' +
            '}';
    }
}
