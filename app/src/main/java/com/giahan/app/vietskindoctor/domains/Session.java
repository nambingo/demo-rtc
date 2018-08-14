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

    @SerializedName("relationship")
    private String mRelationship;

    @SerializedName("dsession_id")
    private String mDsessionID;

    private String name;

    private String weight;

    private String birthdate;

    private String sex;

    private String time_created;

    @SerializedName("patient_name")
    private String mPatientName;

    @SerializedName("patient_age")
    private String mPatientAge;

    private List<String> photos;



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

    public String getRelationship() {
        return mRelationship;
    }

    public void setRelationship(final String relationship) {
        mRelationship = relationship;
    }

    public String getDsessionID() {
        return mDsessionID;
    }

    public void setDsessionID(final String dsessionID) {
        mDsessionID = dsessionID;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(final String weight) {
        this.weight = weight;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(final String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(final String sex) {
        this.sex = sex;
    }

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(final String time_created) {
        this.time_created = time_created;
    }

    public String getPatientName() {
        return mPatientName;
    }

    public void setPatientName(final String patientName) {
        mPatientName = patientName;
    }

    public String getPatientAge() {
        return mPatientAge;
    }

    public void setPatientAge(final String patientAge) {
        mPatientAge = patientAge;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(final List<String> photos) {
        this.photos = photos;
    }
}
