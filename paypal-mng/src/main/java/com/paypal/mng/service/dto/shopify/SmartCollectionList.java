package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SmartCollectionList
{
    @JsonProperty(value = JsonConstants.SMART_COLLECTIONS)
    private List<SmartCollection> smartCollections;

    public List<SmartCollection> getSmartCollections() {
        return smartCollections;
    }

    public void setSmartCollections(List<SmartCollection> smartCollections) {
        this.smartCollections = smartCollections;
    }
}
