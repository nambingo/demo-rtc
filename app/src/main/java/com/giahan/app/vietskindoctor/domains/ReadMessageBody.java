package com.giahan.app.vietskindoctor.domains;

import java.sql.Timestamp;

public class ReadMessageBody {
    private String dsession_id;

    private String last_read_at;

    public ReadMessageBody(final String dsession_id, final String last_read_at) {
        this.dsession_id = dsession_id;
        this.last_read_at = last_read_at;
    }
}
