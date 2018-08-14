package com.giahan.app.vietskindoctor.domains;

import java.util.List;

public class ListRequestResult {
    List<Session> requests;

    public List<Session> getRequests() {
        return requests;
    }

    public void setRequests(final List<Session> requests) {
        this.requests = requests;
    }
}
