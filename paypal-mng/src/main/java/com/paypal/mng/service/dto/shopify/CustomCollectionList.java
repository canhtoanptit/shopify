package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CustomCollectionList
{
    @JsonProperty(value = JsonConstants.CUSTOM_COLLECTIONS)
    private List<CustomCollection> customCollections;

    public List<CustomCollection> getCustomCollections() {
        return customCollections;
    }

    public void setCustomCollections(List<CustomCollection> customCollections) {
        this.customCollections = customCollections;
    }
}
