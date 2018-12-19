package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("created_at")
    String transTime;

    @SerializedName("id")
    String transId;

    @SerializedName("user_id")
    String userId;

    @SerializedName("from_value")
    String preMoney;

    @SerializedName("to_value")
    String postMoney;

    @SerializedName("reason")
    String resaon;

    @SerializedName("type")
    String transType;

    @SerializedName("reference_id")
    String referenceId;

    public String getTransTime() {
        return transTime;
    }

    public String getTransId() {
        return transId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPreMoney() {
        return preMoney;
    }

    public String getPostMoney() {
        return postMoney;
    }

    public String getResaon() {
        return resaon;
    }

    public String getTransType() {
        return transType;
    }

    public String getReferenceId() {
        return referenceId;
    }
}
