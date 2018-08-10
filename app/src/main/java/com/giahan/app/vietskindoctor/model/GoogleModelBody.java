package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NamVT on 5/19/2018.
 */

public class GoogleModelBody {
    @SerializedName("token")
    private String token;

    @SerializedName("device_token")
    private String device_token;

    @SerializedName("os")
    private String os;

    public GoogleModelBody(String token, String device_token, String os) {
        this.token = token;
        this.device_token = device_token;
        this.os = os;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
