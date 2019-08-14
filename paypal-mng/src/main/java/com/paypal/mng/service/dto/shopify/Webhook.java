package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Webhook
{
    @JsonProperty(value = JsonConstants.WEBHOOK)
    private WebhookContent webhookContent;

    public WebhookContent getWebhookContent() {
        return webhookContent;
    }

    public void setWebhookContent(WebhookContent webhookContent) {
        this.webhookContent = webhookContent;
    }
}
