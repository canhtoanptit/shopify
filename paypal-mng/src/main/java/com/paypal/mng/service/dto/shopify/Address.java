package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {
    @JsonProperty(value = JsonConstants.ID)
    private long id;

    @JsonProperty(value = JsonConstants.EMAIL)
    private String email;

    @JsonProperty(value = JsonConstants.FIRST_NAME)
    private String firstName;

    @JsonProperty(value = JsonConstants.LAST_NAME)
    private String lastName;

    @JsonProperty(value = JsonConstants.COMPANY)
    private String company;

    @JsonProperty(value = JsonConstants.COUNTRY)
    private String country;

    @JsonProperty(value = JsonConstants.CITY)
    private String city;

    @JsonProperty(value = JsonConstants.PROVINCE)
    private String province;

    @JsonProperty(value = JsonConstants.ZIP)
    private String zip;

    @JsonProperty(value = JsonConstants.PHONE)
    private String phone;

    @JsonProperty(value = JsonConstants.ADDRESS1)
    private String address1;

    @JsonProperty(value = JsonConstants.ADDRESS2)
    private String address2;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Override
    public String toString() {
        return "Address{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", company='" + company + '\'' +
            ", country='" + country + '\'' +
            ", city='" + city + '\'' +
            ", province='" + province + '\'' +
            ", zip='" + zip + '\'' +
            ", phone='" + phone + '\'' +
            ", address1='" + address1 + '\'' +
            ", address2='" + address2 + '\'' +
            '}';
    }
}
