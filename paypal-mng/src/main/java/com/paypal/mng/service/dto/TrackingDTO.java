package com.paypal.mng.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.paypal.mng.domain.Tracking} entity.
 */
public class TrackingDTO implements Serializable {

    private Long id;

    @NotNull
    private String trackingNumber;

    @NotNull
    private String trackingCompany;

    @NotNull
    private String trackingUrl;

    private String paypalTrackerId;

    private Instant createdAt;

    private Instant updatedAt;


    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPaypalTrackerId() {
        return paypalTrackerId;
    }

    public void setPaypalTrackerId(String paypalTrackerId) {
        this.paypalTrackerId = paypalTrackerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TrackingDTO trackingDTO = (TrackingDTO) o;
        if (trackingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trackingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TrackingDTO{" +
            "id=" + getId() +
            ", trackingNumber='" + getTrackingNumber() + "'" +
            ", trackingCompany='" + getTrackingCompany() + "'" +
            ", trackingUrl='" + getTrackingUrl() + "'" +
            ", paypalTrackerId='" + getPaypalTrackerId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", order=" + getOrderId() +
            "}";
    }
}
