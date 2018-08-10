package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NamVT on 6/12/2018.
 */

public class Ill {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("photos")
    private List<Photo> photos;
    @SerializedName("choices")
    private Integer[] choices;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Integer[] getChoices() {
        return choices;
    }

    public void setChoices(Integer[] choices) {
        this.choices = choices;
    }
}
