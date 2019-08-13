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
    private Integer tracking_number;

    @NotNull
    private String tracking_url;

    private String paypal_tracker_id;

    private Instant created_at;

    private Instant updated_at;


    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(Integer tracking_number) {
        this.tracking_number = tracking_number;
    }

    public String getTracking_url() {
        return tracking_url;
    }

    public void setTracking_url(String tracking_url) {
        this.tracking_url = tracking_url;
    }

    public String getPaypal_tracker_id() {
        return paypal_tracker_id;
    }

    public void setPaypal_tracker_id(String paypal_tracker_id) {
        this.paypal_tracker_id = paypal_tracker_id;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
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
            ", tracking_number=" + getTracking_number() +
            ", tracking_url='" + getTracking_url() + "'" +
            ", paypal_tracker_id='" + getPaypal_tracker_id() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            ", updated_at='" + getUpdated_at() + "'" +
            ", order=" + getOrderId() +
            "}";
    }
}
