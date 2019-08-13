package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Count
{
    @JsonProperty(value = JsonConstants.COUNT)
    private int count;
}
