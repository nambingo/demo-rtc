package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

public class PassCodeBody {
    @SerializedName("currentPasscode")
    private String currentPasscode;
    @SerializedName("newPasscode")
    private String newPasscode;

    public String getCurrentPasscode() {
        return currentPasscode;
    }

    public void setCurrentPasscode(final String currentPasscode) {
        this.currentPasscode = currentPasscode;
    }

    public String getNewPasscode() {
        return newPasscode;
    }

    public void setNewPasscode(final String newPasscode) {
        this.newPasscode = newPasscode;
    }
}
