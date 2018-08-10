package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pham.duc.nam on 21/05/2018.
 */

public class Doctor {
    private int id;
    private String name;
    private String sex;
    private String birthdate;
    private String certdate;
    private List<String> majors;
    private String degree;
    private String workplace;
    private String city;
    private String avatar_url;
    @SerializedName("intro_text")
    private String intro;
    private Float average_rating;
    private boolean online;
    private String fee;
    @SerializedName("cert_photos")
    private List<Photo> photos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCertdate() {
        return certdate;
    }

    public void setCertdate(String certdate) {
        this.certdate = certdate;
    }

    public List<String> getMajors() {
        return majors;
    }

    public void setMajors(List<String> majors) {
        this.majors = majors;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Float getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(Float average_rating) {
        this.average_rating = average_rating;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void updateData(Doctor doctor) {
        this.id = doctor.id;
        this.name = doctor.name;
        this.sex = doctor.sex;
        this.birthdate = doctor.birthdate;
        this.certdate = doctor.certdate;
        this.majors = doctor.majors;
        this.degree = doctor.degree;
        this.workplace = doctor.workplace;
        this.city = doctor.city;
        this.avatar_url = doctor.avatar_url;
        this.intro = doctor.intro;
        this.average_rating = doctor.average_rating;
        this.online = doctor.online;
        this.fee = doctor.fee;
        this.photos = doctor.photos;
    }
}
