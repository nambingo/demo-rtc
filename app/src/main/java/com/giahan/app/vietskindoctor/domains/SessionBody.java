package com.giahan.app.vietskindoctor.domains;

import java.util.List;

/**
 * Created by pham.duc.nam on 07/06/2018.
 */

public class SessionBody {
    private String description;
    private List<Integer> photoIds;
    private String doctor_id;
    private String weight;

//    public SessionBody(String description, List<Integer> list, String doctorID, String mWeight){
//        this.description = description;
//        this.photoIds = list;
//        this.doctor_id = doctorID;
//        this.weight = mWeight;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(List<Integer> photoIds) {
        this.photoIds = photoIds;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
