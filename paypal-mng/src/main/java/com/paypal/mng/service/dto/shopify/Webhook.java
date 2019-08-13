package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Webhook
{
    @JsonProperty(value = JsonConstants.WEBHOOK)
    private WebhookContent webhookContent;
}
