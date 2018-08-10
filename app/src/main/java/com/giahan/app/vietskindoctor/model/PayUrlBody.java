package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NamVT on 5/25/2018.
 */

public class PayUrlBody {
    @SerializedName("amount")
    private String amount;

    public PayUrlBody(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
