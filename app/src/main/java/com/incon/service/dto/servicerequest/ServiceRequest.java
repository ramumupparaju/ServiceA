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
    @SerializedName("fromDate")
    @Expose
    private Long fromDate;
    @SerializedName("toDate")
    @Expose
    private Long toDate;

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

    public Long getFromDate() {
        return fromDate;
    }

    public void setFromDate(Long fromDate) {
        this.fromDate = fromDate;
    }

    public Long getToDate() {
        return toDate;
    }

    public void setToDate(Long toDate) {
        this.toDate = toDate;
    }
}