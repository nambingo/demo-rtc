package com.giahan.app.vietskindoctor.model;

import com.giahan.app.vietskindoctor.domains.Ill;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NamVT on 6/12/2018.
 */

public class AutoDiagnoseResponse {
    @SerializedName("ills")
    private List<Ill> ills;

    public List<Ill> getIlls() {
        return ills;
    }

    public void setIlls(List<Ill> ills) {
        this.ills = ills;
    }
}
