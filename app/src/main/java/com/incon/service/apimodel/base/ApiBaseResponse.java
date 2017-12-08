package com.incon.service.apimodel.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiBaseResponse {
    @SerializedName("code")
    @Expose
    protected Integer statusCode;
    @SerializedName("message")
    @Expose
    protected String message;
    @SerializedName("success")
    @Expose
    protected boolean isSuccess;


    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
