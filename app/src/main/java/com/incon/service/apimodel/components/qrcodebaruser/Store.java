
package com.incon.service.apimodel.components.qrcodebaruser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {

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
    private Object category;
    @SerializedName("storeEmail")
    @Expose
    private String storeEmail;
    @SerializedName("gstn")
    @Expose
    private String gstn;
    @SerializedName("logoUrl")
    @Expose
    private String logoUrl;

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

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public String getGstn() {
        return gstn;
    }

    public void setGstn(String gstn) {
        this.gstn = gstn;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
