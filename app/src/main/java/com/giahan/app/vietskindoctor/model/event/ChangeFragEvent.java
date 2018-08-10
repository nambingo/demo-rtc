package com.giahan.app.vietskindoctor.model.event;

/**
 * Created by NamVT on 7/5/2018.
 */

public class ChangeFragEvent {
    private int index;

    public ChangeFragEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
