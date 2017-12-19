package com.incon.service.dto.addservicecenter;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.AppConstants;
import com.incon.service.utils.ValidationUtils;

/**
 * Created by MY HOME on 16-Dec-17.
 */

public class AddServiceCenter extends BaseObservable {
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("brandId")
    @Expose
    private Integer brandId;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("contactNo")
    @Expose
    private String contactNo;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("divisionId")
    @Expose
    private Integer divisionId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("gstn")
    @Expose
    private String gstn;

    private transient String categoryName;
    private transient String divisionName;
    private transient String brandName;
    @Bindable
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        notifyChange();
    }
    @Bindable
    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
        notifyChange();
    }
    @Bindable
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
        notifyChange();
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyChange();
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    @Bindable
    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
        notifyChange();
    }
    @Bindable
    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
        notifyChange();
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }
    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyChange();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }
    @Bindable
    public String getGstn() {
        return gstn;
    }

    public void setGstn(String gstn) {
        this.gstn = gstn;
        notifyChange();
    }


    public Pair<String, Integer> validateAddServiceCenter(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 7; i++) {
                fieldId = validateFields(i, true);
                if (fieldId != AppConstants.VALIDATION_SUCCESS) {
                    tag = i + "";
                    break;
                }
            }
        } else {
            fieldId = validateFields(Integer.parseInt(tag), false);
        }

        return new Pair<>(tag, fieldId);
    }

    private int validateFields(int id, boolean emptyValidation) {
        switch (id) {
            case 0:
                if (emptyValidation && TextUtils.isEmpty(getName())) {
                    return AppConstants.AddServiceCenterValidation.NAME_REQ;
                }
                break;

            case 1:
                boolean phoneEmpty = TextUtils.isEmpty(getContactNo());
                if (emptyValidation && phoneEmpty) {
                    return AppConstants.AddServiceCenterValidation.PHONE_REQ;
                } else if (!phoneEmpty && !ValidationUtils.isPhoneNumberValid(getContactNo())) {
                    return AppConstants.AddServiceCenterValidation.PHONE_MIN_DIGITS;
                }
                break;


            case 2:
                boolean emailEmpty = TextUtils.isEmpty(getEmail());
                if (emptyValidation && emailEmpty) {
                    return AppConstants.AddServiceCenterValidation.EMAIL_REQ;
                } else if (!emailEmpty && !ValidationUtils.isValidEmail(getEmail())) {
                    return AppConstants.AddServiceCenterValidation.EMAIL_NOTVALID;
                }
                break;


            case 3:
                boolean userAddress = TextUtils.isEmpty(getAddress());
                if (emptyValidation && userAddress) {
                    return AppConstants.AddServiceCenterValidation.ADDRESS_REQ;
                }
                break;


            case 4:
                boolean createdDateEmpty = TextUtils.isEmpty(getCreatedDate());
                if (emptyValidation && createdDateEmpty) {
                    return AppConstants.AddServiceCenterValidation.CREATED_DATE_REQ;
                }
                break;

            case 5:
                boolean categoryEmpty = TextUtils.isEmpty(getCategoryName());
                if (emptyValidation && categoryEmpty) {
                    return AppConstants.AddServiceCenterValidation.CATEGORY_REQ;
                }
                break;

            case 6:
                boolean divisionEmpty = TextUtils.isEmpty(getDivisionName());
                if (emptyValidation && divisionEmpty) {
                    return AppConstants.AddServiceCenterValidation.DIVISION_REQ;
                }
                break;

            case 7:
                boolean gstnEmpty = TextUtils.isEmpty(getGstn());
                if (emptyValidation && gstnEmpty) {
                    return AppConstants.AddServiceCenterValidation.GSTN_REQ;
                }
                break;

            default:
                return AppConstants.VALIDATION_SUCCESS;
        }
        return AppConstants.VALIDATION_SUCCESS;
    }

}


