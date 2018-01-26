package com.incon.service.dto.updateservicecenter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.dto.registration.AddressInfo;

/**
 * Created by PC on 1/5/2018.
 */

public class UpDateServiceCenter {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("addressInfo")
    @Expose
    private AddressInfo addressInfo;
    @SerializedName("contactNo")
    @Expose
    private String contactNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
