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
    @SerializedName("attendsOn")
    @Expose
    private String attendsOn;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("complaint")
    @Expose
    private String complaint;
    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("estimatedPrice")
    @Expose
    private String estimatedPrice;
    @SerializedName("estimatedTime")
    @Expose
    private String estimatedTime;
    @SerializedName("preferredDateFrom")
    @Expose
    private String preferredDateFrom;
    @SerializedName("preferredDateTo")
    @Expose
    private String preferredDateTo;
    @SerializedName("preferredUser")
    @Expose
    private Integer preferredUser;
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

    public String getAttendsOn() {
        return attendsOn;
    }

    public void setAttendsOn(String attendsOn) {
        this.attendsOn = attendsOn;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(String estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getPreferredDateFrom() {
        return preferredDateFrom;
    }

    public void setPreferredDateFrom(String preferredDateFrom) {
        this.preferredDateFrom = preferredDateFrom;
    }

    public String getPreferredDateTo() {
        return preferredDateTo;
    }

    public void setPreferredDateTo(String preferredDateTo) {
        this.preferredDateTo = preferredDateTo;
    }

    public Integer getPreferredUser() {
        return preferredUser;
    }

    public void setPreferredUser(Integer preferredUser) {
        this.preferredUser = preferredUser;
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