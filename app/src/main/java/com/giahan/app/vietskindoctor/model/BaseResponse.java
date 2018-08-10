package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NamVT on 7/30/2018.
 */

public class BaseResponse {
    @SerializedName("error")
    private String error;
    @SerializedName("errorCode")
    private String errorCode;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
