package com.incon.service.dto.updatestatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.components.updatestatus.Status;

/**
 * Created by PC on 1/5/2018.
 */

public class UpDateStatus {
    @SerializedName("assignedTo")
    @Expose
    private Integer assignedTo;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("purchaseId")
    @Expose
    private Integer purchaseId;
    @SerializedName("requestid")
    @Expose
    private Integer requestid;
    @SerializedName("serviceCenterId")
    @Expose
    private Integer serviceCenterId;
    @SerializedName("status")
    @Expose
    private Status status;

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getRequestid() {
        return requestid;
    }

    public void setRequestid(Integer requestid) {
        this.requestid = requestid;
    }

    public Integer getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(Integer serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
