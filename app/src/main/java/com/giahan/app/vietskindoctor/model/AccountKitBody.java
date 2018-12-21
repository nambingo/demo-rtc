package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

public class AccountKitBody {
    @SerializedName("code")
    private String code;

    @SerializedName("isMobile")
    private int isMobile;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public int getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(final int isMobile) {
        this.isMobile = isMobile;
    }
}
