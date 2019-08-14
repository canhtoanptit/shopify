package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WebhookList
{
    @JsonProperty(value = JsonConstants.WEBHOOKS)
    private List<WebhookContent> webhooks;

    public List<WebhookContent> getWebhooks() {
        return webhooks;
    }

    public void setWebhooks(List<WebhookContent> webhooks) {
        this.webhooks = webhooks;
    }
}
