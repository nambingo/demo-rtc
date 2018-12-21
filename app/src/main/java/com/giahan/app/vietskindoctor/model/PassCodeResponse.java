package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

public class PassCodeResponse {
    @SerializedName("success")
    private int success;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(final int success) {
        this.success = success;
    }
}
