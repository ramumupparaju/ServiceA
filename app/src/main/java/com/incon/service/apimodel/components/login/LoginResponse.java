package com.incon.service.apimodel.components.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.base.ApiBaseResponse;

public class LoginResponse extends ApiBaseResponse {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("usertype")
    @Expose
    private Integer usertype;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("dob")
    @Expose
    private String dobInMillis;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("serviceCenterUserType")
    @Expose
    private String serviceCenterUserType;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;


    @SerializedName("msisdn")
    @Expose
    private String msisdn;

    @SerializedName("uuid")
    @Expose
    private String uuid;
    private String password;
    private transient String confirmPassword;

    public String getServiceCenterUserType() {
        return serviceCenterUserType;
    }

    public void setServiceCenterUserType(String serviceCenterUserType) {
        this.serviceCenterUserType = serviceCenterUserType;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDobInMillis() {
        return dobInMillis;
    }

    public void setDobInMillis(String dobInMillis) {
        this.dobInMillis = dobInMillis;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}