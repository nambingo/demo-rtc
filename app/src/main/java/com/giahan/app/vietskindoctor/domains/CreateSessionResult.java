package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pham.duc.nam on 07/06/2018.
 */

public class CreateSessionResult {
    @SerializedName("last_message_at")
    private String mLastMessageAt;

    @SerializedName("created_at")
    private String mCreateAt;

    private String id;

    @SerializedName("patient_id")
    private String mPatientID;

    private String description;

    @SerializedName("doctor_id")
    private String mDoctorID;

    private String resolved;

    @SerializedName("last_message")
    private String mLastMessage;

    @SerializedName("last_message_user_id")
    private String mLastMessageUserID;

    private String rate;

    @SerializedName("rate_comment")
    private String mRateComment;

    public String getLastMessageAt() {
        return mLastMessageAt;
    }

    public void setLastMessageAt(String lastMessageAt) {
        mLastMessageAt = lastMessageAt;
    }

    public String getCreateAt() {
        return mCreateAt;
    }

    public void setCreateAt(String createAt) {
        mCreateAt = createAt;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDoctorID() {
        return mDoctorID;
    }

    public void setDoctorID(String doctorID) {
        mDoctorID = doctorID;
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
}
