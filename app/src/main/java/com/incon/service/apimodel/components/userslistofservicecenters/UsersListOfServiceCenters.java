package com.incon.service.apimodel.components.userslistofservicecenters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.components.login.ServiceCenterResponse;

/**
 * Created by MY HOME on 30-Dec-17.
 */

public class UsersListOfServiceCenters {
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

    @SerializedName("serviceCenter")
    @Expose
    private ServiceCenterResponse serviceCenter;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("serviceCenterRoleId")
    @Expose
    private Integer serviceCenterRoleId;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;

    public ServiceCenterResponse getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(ServiceCenterResponse serviceCenter) {
        this.serviceCenter = serviceCenter;
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

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getServiceCenterRoleId() {
        return serviceCenterRoleId;
    }

    public void setServiceCenterRoleId(Integer serviceCenterRoleId) {
        this.serviceCenterRoleId = serviceCenterRoleId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
