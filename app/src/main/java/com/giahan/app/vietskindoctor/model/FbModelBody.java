package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NamVT on 5/18/2018.
 */

public class FbModelBody {
    @SerializedName("fbAccessToken")
    private String fbAccessToken;

    @SerializedName("fbUserId")
    private String fbUserId;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    @SerializedName("device_token")
    private String device_token;

    @SerializedName("os")
    private String os;

    public FbModelBody(String fbAccessToken, String fbUserId, String email, String username, String device_token, String os) {
        this.fbAccessToken = fbAccessToken;
        this.fbUserId = fbUserId;
        this.email = email;
        this.username = username;
        this.device_token = device_token;
        this.os = os;
    }

    public String getFbAccessToken() {
        return fbAccessToken;
    }

    public void setFbAccessToken(String fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }

    public String getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(String fbUserId) {
        this.fbUserId = fbUserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
