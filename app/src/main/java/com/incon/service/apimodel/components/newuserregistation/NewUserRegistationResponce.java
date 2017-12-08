package com.incon.service.apimodel.components.newuserregistation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 10/16/2017.
 */

public class NewUserRegistationResponce {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("msisdn")
    @Expose
    private String msisdn;
    @SerializedName("usertype")
    @Expose
    private Integer usertype;
    @SerializedName("country")
    @Expose
    private String country;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}