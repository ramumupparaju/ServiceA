package com.incon.service.dto.addfavorites;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 11/8/2017.
 */

public class AddUserAddress extends BaseObservable {

    @SerializedName("name")
    @Expose
    private String name;
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
    @SerializedName("contact")
    @Expose
    private String contact;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
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

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyChange();
    }

    @Bindable
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        notifyChange();
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

    @Bindable
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
        notifyChange();
    }
}
