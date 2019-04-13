package com.quyenbeo.shopifym.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@FieldNameConstants(level = AccessLevel.PRIVATE)
public class OrderDTO {
    @JsonProperty("app_id")
    Integer appId;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    Date createdAt;

    @JsonProperty("line_items")
    List<LineItem> lineItems;

    @Getter
    @Setter
    static class LineItem {
        String name;
        String variantTitle;
    }

    @JsonProperty("total_price")
    Double totalPrice;
}
