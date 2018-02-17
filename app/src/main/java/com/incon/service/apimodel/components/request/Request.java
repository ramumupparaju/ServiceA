package com.incon.service.apimodel.components.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MY HOME on 25-Dec-17.
 */

public class Request {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("warrantyId")
    @Expose
    private Integer warrantyId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("comments")
    @Expose
    private String comments;

    public Integer getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(Integer warrantyId) {
        this.warrantyId = warrantyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
