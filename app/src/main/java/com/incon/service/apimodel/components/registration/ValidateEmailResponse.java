package com.incon.service.apimodel.components.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidateEmailResponse {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ValidateEmailData data;

    public ValidateEmailResponse() {
        data = new ValidateEmailData();
    }

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

    public ValidateEmailData getData() {
        return data;
    }

    public void setData(ValidateEmailData data) {
        this.data = data;
    }
}
