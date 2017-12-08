package com.incon.service.apimodel.components.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.base.ApiBaseResponse;

public class ValidateOtpResponse extends ApiBaseResponse {

    @SerializedName("errors")
    @Expose
    private Object errors;

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }
}
