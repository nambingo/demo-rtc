package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pham.duc.nam on 12/07/2018.
 */
public class ModelObject {
    @SerializedName("Age")
    private String age;
    @SerializedName("Sex")
    private String sex;
    @SerializedName("Post")
    private Integer[] position;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer[] getPosition() {
        return position;
    }

    public void setPosition(Integer[] position) {
        this.position = position;
    }
}
