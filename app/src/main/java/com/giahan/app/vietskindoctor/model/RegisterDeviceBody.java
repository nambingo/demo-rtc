package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

public class RegisterDeviceBody {

    @SerializedName("token")
    private String mToken;

    @SerializedName("os")
    private String mOs;

    public String getToken() {
        return mToken;
    }

    public void setToken(final String token) {
        mToken = token;
    }

    public String getOs() {
        return mOs;
    }

    public void setOs(final String os) {
        mOs = os;
    }
}
