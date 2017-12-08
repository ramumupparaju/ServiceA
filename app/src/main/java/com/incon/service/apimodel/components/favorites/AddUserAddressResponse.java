package com.incon.service.apimodel.components.favorites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 11/4/2017.
 */

public class AddUserAddressResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("subscriberId")
    @Expose
    private Integer subscriberId;
    @SerializedName("adressType")
    @Expose
    private String adressType;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("pincode")
    @Expose
    private Integer pincode;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("contact")
    @Expose
    private String contact;
    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Integer subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getAdressType() {
        return adressType;
    }

    public void setAdressType(String adressType) {
        this.adressType = adressType;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
