package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SessionInfoResult {
    private int id;
    @SerializedName("patient_id")
    private String patientId;
    @SerializedName("doctor_id")
    private String doctorId;
    @SerializedName("doctor_name")
    private String doctorName;
    private String description;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("last_message")
    private String lastMessage;
    @SerializedName("relationship")
    private int relationship;
    @SerializedName("dsession_id")
    private int dsessionId;
    private String name;
    private String weight;
    private String birthdate;
    private String sex;
    @SerializedName("photos")
    private List<Photo> photos;
    @SerializedName("patient_name")
    String patientName;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @SerializedName("doctor_avatar_url")
    private String avatarUrlDoctor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }

    public int getDsessionId() {
        return dsessionId;
    }

    public void setDsessionId(int dsessionId) {
        this.dsessionId = dsessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getAvatarUrlDoctor() {
        return avatarUrlDoctor;
    }

    public void setAvatarUrlDoctor(final String avatarUrlDoctor) {
        this.avatarUrlDoctor = avatarUrlDoctor;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(final String doctorName) {
        this.doctorName = doctorName;
    }
}
