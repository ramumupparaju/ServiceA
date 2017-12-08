
package com.incon.service.apimodel.components.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Data {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("s3AccessKey")
    @Expose
    private String s3AccessKey;
    @SerializedName("s3SecretKey")
    @Expose
    private String s3SecretKey;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("lastLoginTime")
    @Expose
    private Object lastLoginTime;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("address_id")
    @Expose
    private Integer addressId;
    @SerializedName("master_account_id")
    @Expose
    private Object masterAccountId;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("timezone_id")
    @Expose
    private Integer timeZoneId;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getS3AccessKey() {
        return s3AccessKey;
    }

    public void setS3AccessKey(String s3AccessKey) {
        this.s3AccessKey = s3AccessKey;
    }

    public String getS3SecretKey() {
        return s3SecretKey;
    }

    public void setS3SecretKey(String s3SecretKey) {
        this.s3SecretKey = s3SecretKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Object getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Object lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Object getMasterAccountId() {
        return masterAccountId;
    }

    public void setMasterAccountId(Object masterAccountId) {
        this.masterAccountId = masterAccountId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public Integer getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(Integer timeZoneId) {
        this.timeZoneId = timeZoneId;
    }


}
