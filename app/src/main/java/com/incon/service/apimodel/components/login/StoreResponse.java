package com.incon.service.apimodel.components.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("contactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("storeEmail")
    @Expose
    private String storeEmail;
    @SerializedName("logoUrl")
    @Expose
    private String logo;
    @SerializedName("gstn")
    @Expose
    private String gstn;
    @SerializedName("openTime")
    @Expose
    private Object openTime;
    @SerializedName("closeTime")
    @Expose
    private Object closeTime;

    public Object getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Object openTime) {
        this.openTime = openTime;
    }

    public Object getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Object closeTime) {
        this.closeTime = closeTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGstn() {
        return gstn;
    }

    public void setGstn(String gstn) {
        this.gstn = gstn;
    }

}