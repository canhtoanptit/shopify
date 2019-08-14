package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.paypal.mng.config.jackson.FlexDateDeserializer;

import java.math.BigDecimal;
import java.util.Date;

public class RecurringApplicationCharge
{
    @JsonProperty(value = JsonConstants.CHARGE_ID)
    private String id;

    @JsonProperty(value = JsonConstants.TEST)
    private Boolean test;

    @JsonProperty(value = JsonConstants.PLAN_NAME)
    private String name;

    @JsonProperty(value = JsonConstants.PLAN_PRICE)
    private BigDecimal price;

    @JsonProperty(value = JsonConstants.TERMS)
    private String terms;

    // Pending/Accepted/Declined/Active/Frozen/Canceled
    @JsonProperty(value = JsonConstants.CHARGE_STATUS)
    private String status;

    @JsonProperty(value = JsonConstants.BILLING_ON)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    private Date billingOn;

    @JsonProperty(value = JsonConstants.CREATED_AT)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    private Date createdAt;

    @JsonProperty(value = JsonConstants.UPDATED_AT)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    private Date updatedAt;

    @JsonProperty(value = JsonConstants.ACTIVATED_ON)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    private Date activatedOn;

    @JsonProperty(value = JsonConstants.TRIAL_ENDS_ON)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    private Date trialEndsOn;

    @JsonProperty(value = JsonConstants.CANCELED_ON)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    private Date canceledOn;

    @JsonProperty(value = JsonConstants.TRIAL_DAYS)
    private Long trialDays;

    @JsonProperty(value = JsonConstants.CAPPED_AMOUNT)
    private String cappedAmount;

    @JsonProperty(value = JsonConstants.BALANCE_USED)
    private BigDecimal balanceUsed;

    @JsonProperty(value = JsonConstants.BALANCE_REMAINING)
    private BigDecimal balanceRemaining;

    @JsonProperty(value = JsonConstants.RISK_LEVEL)
    private BigDecimal riskLevel;

    @JsonProperty(value = JsonConstants.RETURN_URL)
    private String returnUrl;

    @JsonProperty(value = JsonConstants.DECORATED_RETURN_URL)
    private String decoratedReturnUrl;

    @JsonProperty(value = JsonConstants.CONFIRMATION_URL)
    private String confirmationUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getTest() {
        return test;
    }

    public void setTest(Boolean test) {
        this.test = test;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBillingOn() {
        return billingOn;
    }

    public void setBillingOn(Date billingOn) {
        this.billingOn = billingOn;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getActivatedOn() {
        return activatedOn;
    }

    public void setActivatedOn(Date activatedOn) {
        this.activatedOn = activatedOn;
    }

    public Date getTrialEndsOn() {
        return trialEndsOn;
    }

    public void setTrialEndsOn(Date trialEndsOn) {
        this.trialEndsOn = trialEndsOn;
    }

    public Date getCanceledOn() {
        return canceledOn;
    }

    public void setCanceledOn(Date canceledOn) {
        this.canceledOn = canceledOn;
    }

    public Long getTrialDays() {
        return trialDays;
    }

    public void setTrialDays(Long trialDays) {
        this.trialDays = trialDays;
    }

    public String getCappedAmount() {
        return cappedAmount;
    }

    public void setCappedAmount(String cappedAmount) {
        this.cappedAmount = cappedAmount;
    }

    public BigDecimal getBalanceUsed() {
        return balanceUsed;
    }

    public void setBalanceUsed(BigDecimal balanceUsed) {
        this.balanceUsed = balanceUsed;
    }

    public BigDecimal getBalanceRemaining() {
        return balanceRemaining;
    }

    public void setBalanceRemaining(BigDecimal balanceRemaining) {
        this.balanceRemaining = balanceRemaining;
    }

    public BigDecimal getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(BigDecimal riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getDecoratedReturnUrl() {
        return decoratedReturnUrl;
    }

    public void setDecoratedReturnUrl(String decoratedReturnUrl) {
        this.decoratedReturnUrl = decoratedReturnUrl;
    }

    public String getConfirmationUrl() {
        return confirmationUrl;
    }

    public void setConfirmationUrl(String confirmationUrl) {
        this.confirmationUrl = confirmationUrl;
    }
}
