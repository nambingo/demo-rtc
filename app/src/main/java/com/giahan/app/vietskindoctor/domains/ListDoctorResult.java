package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by pham.duc.nam on 21/05/2018.
 */

public class ListDoctorResult {
    @SerializedName("doctors")
    List<Doctor> mDoctorList;

    public List<Doctor> getDoctorList() {
        return mDoctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        mDoctorList = doctorList;
    }
}
