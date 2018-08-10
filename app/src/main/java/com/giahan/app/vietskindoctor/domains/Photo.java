package com.giahan.app.vietskindoctor.domains;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NamVT on 6/12/2018.
 */

public class Photo {
    @SerializedName("id")
    private int id;
    @SerializedName("url")
    private String url;

    private Bitmap bitmap;

    public Photo(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
