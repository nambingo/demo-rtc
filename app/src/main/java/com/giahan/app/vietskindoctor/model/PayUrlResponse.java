package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NamVT on 5/25/2018.
 */

public class PayUrlResponse {
    @SerializedName("payUrl")
    @Expose
    private String payUrl;
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    public PayUrlResponse(String payUrl, String accessToken) {
        this.payUrl = payUrl;
        this.accessToken = accessToken;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
