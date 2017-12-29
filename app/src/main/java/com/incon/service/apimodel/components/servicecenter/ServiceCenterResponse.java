package com.incon.service.apimodel.components.servicecenter;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 12/20/2017.
 */
public class ServiceCenterResponse extends BaseObservable implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("divisionId")
    @Expose
    private Integer divisionId;
    @SerializedName("brandId")
    @Expose
    private Integer brandId;
    @SerializedName("contactNo")
    @Expose
    private String contactNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("createdDate")
    @Expose
    private Long createdDate;
    @SerializedName("gstn")
    @Expose
    private String gstn;
    @SerializedName("addressInfo")
    @Expose
    private Object addressInfo;

    @SerializedName("logoUrl")
    @Expose
    private String logoUrl;

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getGstn() {
        return gstn;
    }

    public void setGstn(String gstn) {
        this.gstn = gstn;
    }

    public Object getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(Object addressInfo) {
        this.addressInfo = addressInfo;
    }

    protected ServiceCenterResponse(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        location = in.readString();
        address = in.readString();
        categoryId = in.readByte() == 0x00 ? null : in.readInt();
        divisionId = in.readByte() == 0x00 ? null : in.readInt();
        brandId = in.readByte() == 0x00 ? null : in.readInt();
        contactNo = in.readString();
        email = in.readString();
        createdBy = in.readByte() == 0x00 ? null : in.readInt();
        createdDate = in.readByte() == 0x00 ? null : in.readLong();
        gstn = in.readString();
        addressInfo = (Object) in.readValue(Object.class.getClassLoader());
        logoUrl = in.readString();
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
        dest.writeString(location);
        dest.writeString(address);
        if (categoryId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(categoryId);
        }
        if (divisionId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(divisionId);
        }
        if (brandId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(brandId);
        }
        dest.writeString(contactNo);
        dest.writeString(email);
        if (createdBy == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(createdBy);
        }
        if (createdDate == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(createdDate);
        }
        dest.writeString(gstn);
        dest.writeValue(addressInfo);
        dest.writeString(logoUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ServiceCenterResponse> CREATOR = new Parcelable.Creator<ServiceCenterResponse>() {
        @Override
        public ServiceCenterResponse createFromParcel(Parcel in) {
            return new ServiceCenterResponse(in);
        }

        @Override
        public ServiceCenterResponse[] newArray(int size) {
            return new ServiceCenterResponse[size];
        }
    };
}