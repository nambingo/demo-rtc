package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by pham.duc.nam on 08/06/2018.
 */

public class Session {
    private int mType;

    private String id;

    private String status;

    @SerializedName("patient_id")
    private String mPatientID;

    @SerializedName("doctor_id")
    private String mDoctorID;

    private String description;

    @SerializedName("created_at")
    private String mCreateAt;

    private String resolved;

    @SerializedName("last_message")
    private String mLastMessage;

    @SerializedName("last_message_user_id")
    private String mLastMessageUserID;

    @SerializedName("last_message_at")
    private String mLastMessageAt;

    private String rate;

    @SerializedName("rate_comment")
    private String mRateComment;

    @SerializedName("last_read_at")
    private String mLastReadAt;

    @SerializedName("doctor_name")
    private String mDoctorName;

    @SerializedName("doctor_avatar_url")
    private String mDoctorAvatarUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientID() {
        return mPatientID;
    }

    public void setPatientID(String patientID) {
        mPatientID = patientID;
    }

    public String getDoctorID() {
        return mDoctorID;
    }

    public void setDoctorID(String doctorID) {
        mDoctorID = doctorID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateAt() {
        return mCreateAt;
    }

    public void setCreateAt(String createAt) {
        mCreateAt = createAt;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getLastMessage() {
        return mLastMessage;
    }

    public void setLastMessage(String lastMessage) {
        mLastMessage = lastMessage;
    }

    public String getLastMessageUserID() {
        return mLastMessageUserID;
    }

    public void setLastMessageUserID(String lastMessageUserID) {
        mLastMessageUserID = lastMessageUserID;
    }

    public String getLastMessageAt() {
        return mLastMessageAt;
    }

    public void setLastMessageAt(String lastMessageAt) {
        mLastMessageAt = lastMessageAt;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRateComment() {
        return mRateComment;
    }

    public void setRateComment(String rateComment) {
        mRateComment = rateComment;
    }

    public String getLastReadAt() {
        return mLastReadAt;
    }

    public void setLastReadAt(String lastReadAt) {
        mLastReadAt = lastReadAt;
    }

    public String getDoctorName() {
        return mDoctorName;
    }

    public void setDoctorName(String doctorName) {
        mDoctorName = doctorName;
    }

    public String getDoctorAvatarUrl() {
        return mDoctorAvatarUrl;
    }

    public void setDoctorAvatarUrl(String doctorAvatarUrl) {
        mDoctorAvatarUrl = doctorAvatarUrl;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
