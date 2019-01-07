package com.giahan.app.vietskindoctor.model;

import com.google.gson.annotations.SerializedName;

public class WithdrawBody {
    @SerializedName("amount")
    String withdrawAmount;

    @SerializedName("passcode")
    String passcode;

    public WithdrawBody(String withdrawAmount, String passcode) {
        this.withdrawAmount = withdrawAmount;
        this.passcode = passcode;
    }

    public String getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(String withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
