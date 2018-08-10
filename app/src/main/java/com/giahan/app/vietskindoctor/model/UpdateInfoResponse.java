package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NamVT on 6/20/2018.
 */

public class UpdateInfoResponse {
    @SerializedName("success")
    private Integer success;
    @SerializedName("access_token")
    private String accessToken;

    public UpdateInfoResponse(Integer success, String accessToken) {
        this.success = success;
        this.accessToken = accessToken;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
