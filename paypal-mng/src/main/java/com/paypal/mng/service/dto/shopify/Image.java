package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Image
{
    @JsonProperty(value = JsonConstants.SRC)
    private String src;

    @JsonProperty(value = JsonConstants.POSITION)
    private Integer position;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
