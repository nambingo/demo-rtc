package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

public class PasscodeLoginBody {
    @SerializedName("passcode")
    private String passcode;

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(final String passcode) {
        this.passcode = passcode;
    }
}
