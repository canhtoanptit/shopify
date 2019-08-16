package com.paypal.mng.service.dto.shopify;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TransactionList
{
    @JsonProperty(value = JsonConstants.TRANSACTIONS)
    private List<ShopifyTransaction> transactions;

    public List<ShopifyTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<ShopifyTransaction> transactions) {
        this.transactions = transactions;
    }
}
