package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by pham.duc.nam on 11/06/2018.
 */

public class SessionResult {
    @SerializedName("messages")
    private List<Message> mMessageList;

    public List<Message> getMessageList() {
        return mMessageList;
    }

    public void setMessageList(List<Message> messageList) {
        mMessageList = messageList;
    }
}
