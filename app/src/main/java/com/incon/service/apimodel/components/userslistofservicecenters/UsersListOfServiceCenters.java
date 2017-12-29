package com.incon.service.apimodel.components.userslistofservicecenters;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;

/**
 * Created by INCON TECHNOLOGIES on 12/25/2017.
 */

public class UsersListOfServiceCenters extends BaseObservable implements Parcelable {
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

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
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

    protected UsersListOfServiceCenters(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        email = in.readString();
        address = in.readString();
        location = in.readString();
        usertype = in.readByte() == 0x00 ? null : in.readInt();
        serviceCenter = (ServiceCenterResponse) in.readValue(ServiceCenterResponse.class.getClassLoader());
        country = in.readString();
        dob = in.readString();
        gender = in.readString();
        serviceCenterRoleId = in.readByte() == 0x00 ? null : in.readInt();
        mobileNumber = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(location);
        if (usertype == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(usertype);
        }
        dest.writeValue(serviceCenter);
        dest.writeString(country);
        dest.writeString(dob);
        dest.writeString(gender);
        if (serviceCenterRoleId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(serviceCenterRoleId);
        }
        dest.writeString(mobileNumber);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UsersListOfServiceCenters> CREATOR = new Parcelable.Creator<UsersListOfServiceCenters>() {
        @Override
        public UsersListOfServiceCenters createFromParcel(Parcel in) {
            return new UsersListOfServiceCenters(in);
        }

        @Override
        public UsersListOfServiceCenters[] newArray(int size) {
            return new UsersListOfServiceCenters[size];
        }
    };
}