package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NamVT on 6/12/2018.
 */

public class AutoDiagnoseBody {
    @SerializedName("choices")
    private String [] choices;

    public AutoDiagnoseBody(String[] choices) {
        this.choices = choices;
    }

    public String[] getChoices() {
        return choices;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }
}
