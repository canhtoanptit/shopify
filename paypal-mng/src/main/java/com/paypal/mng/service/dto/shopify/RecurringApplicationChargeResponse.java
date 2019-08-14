package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecurringApplicationChargeResponse
{
    @JsonProperty(value = JsonConstants.RECURRING_APPLICATION_CHARGE)
    private RecurringApplicationCharge recurringApplicationCharge;

    public RecurringApplicationCharge getRecurringApplicationCharge() {
        return recurringApplicationCharge;
    }

    public void setRecurringApplicationCharge(RecurringApplicationCharge recurringApplicationCharge) {
        this.recurringApplicationCharge = recurringApplicationCharge;
    }
}
