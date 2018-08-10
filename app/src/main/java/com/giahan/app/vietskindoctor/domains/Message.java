package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Created by pham.duc.nam on 12/06/2018.
 */

public class Message {

    public static final String TYPE_TEXT = "1";
    public static final String TYPE_FILE = "2";
    public static final String TYPE_TEST_SAMPLE = "3";
    public static final String TYPE_PRESCRIPTION = "4";
    public static final String TYPE_DATE = "date";


    private int mStatus;

    @SerializedName("created_at")
    private String createdAt;

    private String id;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("dsession_id")
    private String dsessionId;

    private String type;

    private String message;

    @SerializedName("obj_id")
    private String onjId;

    @SerializedName("obj_url")
    private String objUrl;

    private boolean isClick = false;

    public int getStatus(){
        return mStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDsessionId() {
        return dsessionId;
    }

    public void setDsessionId(String dsessionId) {
        this.dsessionId = dsessionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOnjId() {
        return onjId;
    }

    public void setOnjId(String onjId) {
        this.onjId = onjId;
    }

    public String getObjUrl() {
        return objUrl;
    }

    public void setObjUrl(String objUrl) {
        this.objUrl = objUrl;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
