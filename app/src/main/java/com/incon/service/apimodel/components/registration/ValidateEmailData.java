package com.incon.service.apimodel.components.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidateEmailData {

    @SerializedName("exist")
    @Expose
    private boolean exist;

    public boolean getExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
