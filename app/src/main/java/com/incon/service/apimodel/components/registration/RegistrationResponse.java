
package com.incon.service.apimodel.components.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.base.ApiBaseResponse;

import java.util.List;

public class RegistrationResponse extends ApiBaseResponse {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("errors")
    @Expose
    private List<String> errors = null;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }


}
