package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

public class AcceptRequestBody {
    @SerializedName("confirm")
    private boolean isConfirm;

    @SerializedName("dsession_id")
    private String mDesessionID;

    public AcceptRequestBody(final boolean isConfirm, final String desessionID) {
        this.isConfirm = isConfirm;
        mDesessionID = desessionID;
    }
}
