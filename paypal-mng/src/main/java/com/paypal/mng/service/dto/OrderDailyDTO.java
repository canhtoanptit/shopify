package com.paypal.mng.service.dto;

import com.opencsv.bean.CsvBindByPosition;

import java.io.Serializable;
import java.util.Date;

/**
 * A OrderDaily.
 */
public class OrderDailyDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @CsvBindByPosition(position = 0)
    private Long id;

    @CsvBindByPosition(position = 1)
    private String name;

    @CsvBindByPosition(position = 2)
    private String email;

    @CsvBindByPosition(position = 3)
    private String financialStatus;

    @CsvBindByPosition(position = 4)
    private Date paidAt;

    @CsvBindByPosition(position = 5)
    private Integer lineItemQuantity;

    @CsvBindByPosition(position = 6)
    private String lineItemName;

    @CsvBindByPosition(position = 7)
    private String shipingName;

    @CsvBindByPosition(position = 8)
    private String shipingAddress;

    @CsvBindByPosition(position = 9)
    private String shipingStreet;

    @CsvBindByPosition(position = 10)
    private String shipingAddress2;

    @CsvBindByPosition(position = 11)
    private String shipingCompany;

    @CsvBindByPosition(position = 12)
    private String shipingCity;

    @CsvBindByPosition(position = 13)
    private String shipingZip;

    @CsvBindByPosition(position = 14)
    private String shipingProvince;

    @CsvBindByPosition(position = 15)
    private String shipingCountry;

    @CsvBindByPosition(position = 16)
    private String shipingPhone;

    @CsvBindByPosition(position = 17)
    private String note;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public OrderDailyDTO name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public OrderDailyDTO email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFinancialStatus() {
        return financialStatus;
    }

    public OrderDailyDTO financialStatus(String financialStatus) {
        this.financialStatus = financialStatus;
        return this;
    }

    public void setFinancialStatus(String financialStatus) {
        this.financialStatus = financialStatus;
    }

    public Date getPaidAt() {
        return paidAt;
    }

    public OrderDailyDTO paidAt(Date paidAt) {
        this.paidAt = paidAt;
        return this;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }

    public Integer getLineItemQuantity() {
        return lineItemQuantity;
    }

    public OrderDailyDTO lineItemQuantity(Integer lineItemQuantity) {
        this.lineItemQuantity = lineItemQuantity;
        return this;
    }

    public void setLineItemQuantity(Integer lineItemQuantity) {
        this.lineItemQuantity = lineItemQuantity;
    }

    public String getLineItemName() {
        return lineItemName;
    }

    public OrderDailyDTO lineItemName(String lineItemName) {
        this.lineItemName = lineItemName;
        return this;
    }

    public void setLineItemName(String lineItemName) {
        this.lineItemName = lineItemName;
    }

    public String getShipingName() {
        return shipingName;
    }

    public OrderDailyDTO shipingName(String shipingName) {
        this.shipingName = shipingName;
        return this;
    }

    public void setShipingName(String shipingName) {
        this.shipingName = shipingName;
    }

    public String getShipingAddress() {
        return shipingAddress;
    }

    public OrderDailyDTO shipingAddress(String shipingAddress) {
        this.shipingAddress = shipingAddress;
        return this;
    }

    public void setShipingAddress(String shipingAddress) {
        this.shipingAddress = shipingAddress;
    }

    public String getShipingStreet() {
        return shipingStreet;
    }

    public OrderDailyDTO shipingStreet(String shipingStreet) {
        this.shipingStreet = shipingStreet;
        return this;
    }

    public void setShipingStreet(String shipingStreet) {
        this.shipingStreet = shipingStreet;
    }

    public String getShipingAddress2() {
        return shipingAddress2;
    }

    public OrderDailyDTO shipingAddress2(String shipingAddress2) {
        this.shipingAddress2 = shipingAddress2;
        return this;
    }

    public void setShipingAddress2(String shipingAddress2) {
        this.shipingAddress2 = shipingAddress2;
    }

    public String getShipingCompany() {
        return shipingCompany;
    }

    public OrderDailyDTO shipingCompany(String shipingCompany) {
        this.shipingCompany = shipingCompany;
        return this;
    }

    public void setShipingCompany(String shipingCompany) {
        this.shipingCompany = shipingCompany;
    }

    public String getShipingCity() {
        return shipingCity;
    }

    public OrderDailyDTO shipingCity(String shipingCity) {
        this.shipingCity = shipingCity;
        return this;
    }

    public void setShipingCity(String shipingCity) {
        this.shipingCity = shipingCity;
    }

    public String getShipingZip() {
        return shipingZip;
    }

    public OrderDailyDTO shipingZip(String shipingZip) {
        this.shipingZip = shipingZip;
        return this;
    }

    public void setShipingZip(String shipingZip) {
        this.shipingZip = shipingZip;
    }

    public String getShipingProvince() {
        return shipingProvince;
    }

    public OrderDailyDTO shipingProvince(String shipingProvince) {
        this.shipingProvince = shipingProvince;
        return this;
    }

    public void setShipingProvince(String shipingProvince) {
        this.shipingProvince = shipingProvince;
    }

    public String getShipingCountry() {
        return shipingCountry;
    }

    public OrderDailyDTO shipingCountry(String shipingCountry) {
        this.shipingCountry = shipingCountry;
        return this;
    }

    public void setShipingCountry(String shipingCountry) {
        this.shipingCountry = shipingCountry;
    }

    public String getShipingPhone() {
        return shipingPhone;
    }

    public OrderDailyDTO shipingPhone(String shipingPhone) {
        this.shipingPhone = shipingPhone;
        return this;
    }

    public void setShipingPhone(String shipingPhone) {
        this.shipingPhone = shipingPhone;
    }

    public String getNote() {
        return note;
    }

    public OrderDailyDTO note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDailyDTO)) {
            return false;
        }
        return id != null && id.equals(((OrderDailyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderDaily{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", financialStatus='" + getFinancialStatus() + "'" +
            ", paidAt='" + getPaidAt() + "'" +
            ", lineItemQuantity=" + getLineItemQuantity() +
            ", lineItemName='" + getLineItemName() + "'" +
            ", shipingName='" + getShipingName() + "'" +
            ", shipingAddress='" + getShipingAddress() + "'" +
            ", shipingStreet='" + getShipingStreet() + "'" +
            ", shipingAddress2='" + getShipingAddress2() + "'" +
            ", shipingCompany='" + getShipingCompany() + "'" +
            ", shipingCity='" + getShipingCity() + "'" +
            ", shipingZip='" + getShipingZip() + "'" +
            ", shipingProvince='" + getShipingProvince() + "'" +
            ", shipingCountry='" + getShipingCountry() + "'" +
            ", shipingPhone='" + getShipingPhone() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
