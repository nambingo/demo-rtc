package com.giahan.app.vietskindoctor.domains;

import java.util.List;

/**
 * Created by pham.duc.nam on 07/06/2018.
 */

public class RequestDoctorBody {

    private String dsession_id;
    private String doctor_id;

    public RequestDoctorBody(String dsessionID, String doctorID){
        this.dsession_id = dsessionID;
        this.doctor_id = doctorID;
    }
}
