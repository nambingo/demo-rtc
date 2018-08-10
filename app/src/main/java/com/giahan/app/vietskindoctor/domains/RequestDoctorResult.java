package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pham.duc.nam on 07/06/2018.
 */

public class RequestDoctorResult {
    @SerializedName("created_at")
    private String mCreatedAt;

    private String id;

    @SerializedName("patient_id")
    private String mPatientID;

    @SerializedName("doctor_id")
    private String mDoctorID;

    @SerializedName("dsession_id")
    private String mDsessionID;

    private String expired;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
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

    public String getDoctorID() {
        return mDoctorID;
    }

    public void setDoctorID(String doctorID) {
        mDoctorID = doctorID;
    }

    public String getDsessionID() {
        return mDsessionID;
    }

    public void setDsessionID(String dsessionID) {
        mDsessionID = dsessionID;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }
}
