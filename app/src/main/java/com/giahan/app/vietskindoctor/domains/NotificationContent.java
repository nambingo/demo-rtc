package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

public class NotificationContent {

    @SerializedName("dsessionId")
    private String mSessionID;

    public String getNotiType() {
        return notiType;
    }

    public void setNotiType(String notiType) {
        this.notiType = notiType;
    }

    @SerializedName("type")
    private String notiType;

    public String getmNotificationMessage() {
        return mNotificationMessage;
    }

    public void setmNotificationMessage(String mNotificationMessage) {
        this.mNotificationMessage = mNotificationMessage;
    }

    @SerializedName("notification_message")
    private String mNotificationMessage;

    public String getmSessionID() {
        return mSessionID;
    }

    public void setmSessionID(String mSessionID) {
        this.mSessionID = mSessionID;
    }
}
