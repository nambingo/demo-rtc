package com.giahan.app.vietskindoctor.domains;

/**
 * Created by pham.duc.nam on 12/06/2018.
 */

public class MessageBody {
    private String dsession_id;
    private int type;
    private String message;
    private String obj_id;
    private String obj_url;

    public MessageBody(String dsessionID, int type, String message, String obj_id, String obj_url){
        this.dsession_id = dsessionID;
        this.type = type;
        this.message = message;
        this.obj_id = obj_id;
        this.obj_url = obj_url;
    }
}
