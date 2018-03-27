package com.incon.service.apimodel.components.getstatuslist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MY HOME on 29-Dec-17.
 */

public class DefaultStatusData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object statusData) {
        if (statusData instanceof DefaultStatusData) {
            DefaultStatusData statusData1 = ((DefaultStatusData) statusData);
            if (statusData1 != null) {
                if (statusData1.getId() != null) {
                    return (statusData1.getId() == id);
                } else {
                    return (statusData1.getCode() == code);
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

