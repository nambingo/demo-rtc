package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransactionList {
    @SerializedName("logs")
    List<Transaction> mTransList;

    public TransactionList(List<Transaction> mTransList) {
        this.mTransList = mTransList;
    }

    public List<Transaction> getmTransList() {
        return mTransList;
    }
}
