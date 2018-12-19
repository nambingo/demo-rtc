package com.giahan.app.vietskindoctor.domains;

import com.google.gson.annotations.SerializedName;

import org.androidannotations.annotations.SupposeUiThread;

public class WithdrawalResult {

    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("id")
    private Integer id;
    @SerializedName("doctor_id")
    private Integer doctorId;
    @SerializedName("amount")
    private Integer amount;
    @SerializedName("status")
    private String status;
    @SerializedName("reject_reason")
    private Object rejectReason;
    @SerializedName("transaction_note")
    private String transactionNote;

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public Object getRejectReason() {
        return rejectReason;
    }

    public String getTransactionNote() {
        return transactionNote;
    }
}
