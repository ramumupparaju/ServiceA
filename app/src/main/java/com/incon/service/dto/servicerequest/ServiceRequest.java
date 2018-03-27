package com.incon.service.dto.servicerequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceRequest {

    @SerializedName("serviceIds")
    @Expose
    private String serviceIds;
    @SerializedName("assignedUser")
    @Expose
    private Integer assignedUser;
    @SerializedName("status")
    @Expose
    private String status;
    private Long reqDate;
    private Long createdDate;

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(Integer assignedUser) {
        this.assignedUser = assignedUser;
    }

    public String getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(String serviceIds) {
        this.serviceIds = serviceIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getReqDate() {
        return reqDate;
    }

    public void setReqDate(Long reqDate) {
        this.reqDate = reqDate;
    }
}