package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.paypal.mng.config.jackson.FlexDateDeserializer;

import java.util.Date;

public class WebhookContent
{
    @JsonProperty(value = JsonConstants.ID)
    private Long id;

    @JsonProperty(value = JsonConstants.TOPIC)
    private String topic;

    @JsonProperty(value = JsonConstants.ADDRESS)
    private String address;

    @JsonProperty(value = JsonConstants.FORMAT)
    private String format;

    @JsonProperty(value = JsonConstants.CREATED_AT)
    @JsonDeserialize(using = FlexDateDeserializer.class)
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
