package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Collect
{
    @JsonProperty(value = JsonConstants.ID)
    private long id;

    @JsonProperty(value = JsonConstants.PRODUCT_ID)
    private long productId;

    @JsonProperty(value = JsonConstants.COLLECTION_ID)
    private long collectionId;
}
