package com.incon.service.apimodel.components.updatestatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 1/5/2018.
 */

public class Status {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;

    public Status(Integer id, String code) {
        this.id = id;
        this.code = code;
    }

    public Status(String code) {
        this.code = code;
    }

    public Status(Integer id) {
        this.id = id;
    }

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
    public boolean equals(Object obj) {


        if (!(obj instanceof Status)) {
            return false;
        }

        Status status = (Status) obj;
        return status.id.equals(id);
    }
}
