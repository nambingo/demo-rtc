package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WithdrawListReSult {

    @SerializedName("withdraws")
    private List<WithdrawalResult> withdraws = null;
    @SerializedName("access_token")
    private String accessToken;

    public List<WithdrawalResult> getWithdraws() {
        return withdraws;
    }

    public void setWithdraws(List<WithdrawalResult> withdraws) {
        this.withdraws = withdraws;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
