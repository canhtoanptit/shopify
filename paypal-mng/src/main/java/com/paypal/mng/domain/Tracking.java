package com.paypal.mng.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Tracking.
 */
@Entity
@Table(name = "tracking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tracking_number", nullable = false, unique = true)
    private String trackingNumber;

    @NotNull
    @Column(name = "tracking_company", nullable = false)
    private String trackingCompany;

    @NotNull
    @Column(name = "tracking_url", nullable = false)
    private String trackingUrl;

    @Column(name = "paypal_tracker_id")
    private String paypalTrackerId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("trackings")
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public Tracking trackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingCompany() {
        return trackingCompany;
    }

    public Tracking trackingCompany(String trackingCompany) {
        this.trackingCompany = trackingCompany;
        return this;
    }

    public void setTrackingCompany(String trackingCompany) {
        this.trackingCompany = trackingCompany;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public Tracking trackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
        return this;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public String getPaypalTrackerId() {
        return paypalTrackerId;
    }

    public Tracking paypalTrackerId(String paypalTrackerId) {
        this.paypalTrackerId = paypalTrackerId;
        return this;
    }

    public void setPaypalTrackerId(String paypalTrackerId) {
        this.paypalTrackerId = paypalTrackerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Tracking createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Tracking updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Order getOrder() {
        return order;
    }

    public Tracking order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tracking)) {
            return false;
        }
        return id != null && id.equals(((Tracking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Tracking{" +
            "id=" + getId() +
            ", trackingNumber='" + getTrackingNumber() + "'" +
            ", trackingCompany='" + getTrackingCompany() + "'" +
            ", trackingUrl='" + getTrackingUrl() + "'" +
            ", paypalTrackerId='" + getPaypalTrackerId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
