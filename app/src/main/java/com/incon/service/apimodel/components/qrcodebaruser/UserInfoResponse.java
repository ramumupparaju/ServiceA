
package com.incon.service.apimodel.components.qrcodebaruser;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("mobileNumber")
    @Expose
    private String msisdn;
    @SerializedName("usertype")
    @Expose
    private Integer usertype;
    @SerializedName("store")
    @Expose
    private Store store;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;

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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
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


    protected UserInfoResponse(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        email = in.readString();
        location = in.readString();
        msisdn = in.readString();
        usertype = in.readByte() == 0x00 ? null : in.readInt();
        store = (Store) in.readValue(Store.class.getClassLoader());
        uuid = in.readString();
        country = in.readString();
        dob = in.readString();
        gender = in.readString();
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
        dest.writeString(location);
        dest.writeString(msisdn);
        if (usertype == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(usertype);
        }
        dest.writeValue(store);
        dest.writeString(uuid);
        dest.writeString(country);
        dest.writeString(dob);
        dest.writeString(gender);
    }

    @SuppressWarnings("unused")
    public static final Creator<UserInfoResponse> CREATOR =
            new Creator<UserInfoResponse>() {
        @Override
        public UserInfoResponse createFromParcel(Parcel in) {
            return new UserInfoResponse(in);
        }

        @Override
        public UserInfoResponse[] newArray(int size) {
            return new UserInfoResponse[size];
        }
    };
}