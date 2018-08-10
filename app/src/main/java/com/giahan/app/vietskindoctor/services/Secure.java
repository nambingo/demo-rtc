package com.giahan.app.vietskindoctor.services;

import com.giahan.app.vietskindoctor.utils.Constant;

/**
 * Created by FRAMGIA\pham.duc.nam on 17/05/2018.
 */

public class Secure {
    private static Secure secure;
    private String baseUrl;
    private static String baseUrlApi = "https://api.dev.vietskin.vn/";
    private String baseUrlStatus;

    public static Secure self() {
        if (secure == null) secure = new Secure();
        return secure;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrlApi(boolean isMedia) {
        return isMedia ? Constant.URL_MEDIA : Constant.URL_API;
    }

    public void setBaseUrlApi(String baseUrlApi) {
        this.baseUrlApi = baseUrlApi;
    }

    public String getBaseUrlStatus() {
        return baseUrlStatus;
    }

    public void setBaseUrlStatus(String baseUrlStatus) {
        this.baseUrlStatus = baseUrlStatus;
    }
}
