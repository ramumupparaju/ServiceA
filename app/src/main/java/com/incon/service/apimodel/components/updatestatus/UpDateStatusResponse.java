package com.incon.service.apimodel.components.updatestatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 1/5/2018.
 */

public class UpDateStatusResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("requestid")
    @Expose
    private Integer requestid;
    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("serviceCenterId")
    @Expose
    private Integer serviceCenterId;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("createdBy")
    @Expose
    private Object createdBy;
    @SerializedName("createdDate")
    @Expose
    private Integer createdDate;
    @SerializedName("assignedTo")
    @Expose
    private Integer assignedTo;
    @SerializedName("attendsOn")
    @Expose
    private Object attendsOn;
    @SerializedName("complaint")
    @Expose
    private Object complaint;
    @SerializedName("preferredDateFrom")
    @Expose
    private Object preferredDateFrom;
    @SerializedName("preferredDateTo")
    @Expose
    private Object preferredDateTo;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("estimatedPrice")
    @Expose
    private Object estimatedPrice;
    @SerializedName("estimatedTime")
    @Expose
    private Object estimatedTime;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("readStatus")
    @Expose
    private Integer readStatus;
    @SerializedName("preferredUser")
    @Expose
    private Object preferredUser;
    @SerializedName("purchaseId")
    @Expose
    private Integer purchaseId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequestid() {
        return requestid;
    }

    public void setRequestid(Integer requestid) {
        this.requestid = requestid;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(Integer serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Integer createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Integer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Object getAttendsOn() {
        return attendsOn;
    }

    public void setAttendsOn(Object attendsOn) {
        this.attendsOn = attendsOn;
    }

    public Object getComplaint() {
        return complaint;
    }

    public void setComplaint(Object complaint) {
        this.complaint = complaint;
    }

    public Object getPreferredDateFrom() {
        return preferredDateFrom;
    }

    public void setPreferredDateFrom(Object preferredDateFrom) {
        this.preferredDateFrom = preferredDateFrom;
    }

    public Object getPreferredDateTo() {
        return preferredDateTo;
    }

    public void setPreferredDateTo(Object preferredDateTo) {
        this.preferredDateTo = preferredDateTo;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Object getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(Object estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    public Object getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Object estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public Object getPreferredUser() {
        return preferredUser;
    }

    public void setPreferredUser(Object preferredUser) {
        this.preferredUser = preferredUser;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }
}
