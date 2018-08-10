package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NamVT on 6/7/2018.
 */

public class InfoUpdateBody {
    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    @SerializedName("birthdate")
    private String birthdate;

    @SerializedName("phone")
    private String phone;

    @SerializedName("sex")
    private String sex;

    public InfoUpdateBody(String name, String birthdate, String phone, String sex) {
        this.name = name;
        this.birthdate = birthdate;
        this.phone = phone;
        this.sex = sex;
    }

    public InfoUpdateBody(String email) {
        this.email = email;
    }

    public InfoUpdateBody(String birthdate, String phone, String sex) {
        this.birthdate = birthdate;
        this.phone = phone;
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
