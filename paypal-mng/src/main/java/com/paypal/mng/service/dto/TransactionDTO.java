package com.paypal.mng.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.paypal.mng.domain.Transaction} entity.
 */
public class TransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private String authorization;

    private Instant created_at;

    private Instant updated_at;


    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
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

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if (transactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", authorization='" + getAuthorization() + "'" +
            ", created_at='" + getCreated_at() + "'" +
            ", updated_at='" + getUpdated_at() + "'" +
            ", order=" + getOrderId() +
            "}";
    }
}
