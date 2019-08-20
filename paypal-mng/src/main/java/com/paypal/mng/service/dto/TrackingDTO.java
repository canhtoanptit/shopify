package com.paypal.mng.service.dto;

import com.paypal.mng.domain.Order;
import com.paypal.mng.domain.Tracking;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
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

    private Integer orderNumber;

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

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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

    public static TrackingDTO toDto(Tracking tracking) {
        if (tracking == null) {
            return null;
        }

        TrackingDTO trackingDTO = new TrackingDTO();

        trackingDTO.setOrderId(trackingOrderId(tracking));
        trackingDTO.setId(tracking.getId());
        trackingDTO.setTrackingNumber(tracking.getTrackingNumber());
        trackingDTO.setTrackingCompany(tracking.getTrackingCompany());
        trackingDTO.setTrackingUrl(tracking.getTrackingUrl());
        trackingDTO.setPaypalTrackerId(tracking.getPaypalTrackerId());
        trackingDTO.setCreatedAt(tracking.getCreatedAt());
        trackingDTO.setUpdatedAt(tracking.getUpdatedAt());
        trackingDTO.setOrderNumber(trackingOrderNumber(tracking));

        return trackingDTO;
    }

    private static Long trackingOrderId(Tracking tracking) {
        if (tracking == null) {
            return null;
        }
        Order order = tracking.getOrder();
        if (order == null) {
            return null;
        }
        Long id = order.getId();
        if (id == null) {
            return null;
        }
        return id;
    }

    private static Integer trackingOrderNumber(Tracking tracking) {
        if (tracking == null) {
            return null;
        }
        Order order = tracking.getOrder();
        if (order == null) {
            return null;
        }
        return order.getOrderNumber();
    }
}
