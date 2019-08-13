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
    private Integer tracking_number;

    @NotNull
    @Column(name = "tracking_url", nullable = false)
    private String tracking_url;

    @Column(name = "paypal_tracker_id")
    private String paypal_tracker_id;

    @Column(name = "created_at")
    private Instant created_at;

    @Column(name = "updated_at")
    private Instant updated_at;

    @ManyToOne
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTracking_number() {
        return tracking_number;
    }

    public Tracking tracking_number(Integer tracking_number) {
        this.tracking_number = tracking_number;
        return this;
    }

    public void setTracking_number(Integer tracking_number) {
        this.tracking_number = tracking_number;
    }

    public String getTracking_url() {
        return tracking_url;
    }

    public Tracking tracking_url(String tracking_url) {
        this.tracking_url = tracking_url;
        return this;
    }

    public void setTracking_url(String tracking_url) {
        this.tracking_url = tracking_url;
    }

    public String getPaypal_tracker_id() {
        return paypal_tracker_id;
    }

    public Tracking paypal_tracker_id(String paypal_tracker_id) {
        this.paypal_tracker_id = paypal_tracker_id;
        return this;
    }

    public void setPaypal_tracker_id(String paypal_tracker_id) {
        this.paypal_tracker_id = paypal_tracker_id;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public Tracking created_at(Instant created_at) {
        this.created_at = created_at;
        return this;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public Tracking updated_at(Instant updated_at) {
        this.updated_at = updated_at;
        return this;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
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
            ", tracking_number=" + getTracking_number() +
            ", tracking_url='" + getTracking_url() + "'" +
            ", paypal_tracker_id='" + getPaypal_tracker_id() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            ", updated_at='" + getUpdated_at() + "'" +
            "}";
    }
}
