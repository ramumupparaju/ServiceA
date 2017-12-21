package com.incon.service.dto.registration;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.AppConstants;
import com.incon.service.utils.ValidationUtils;

import static com.incon.service.AppConstants.VALIDATION_SUCCESS;

/**
 * Created by MY HOME on 16-Dec-17.
 */

public class ServiceCenter extends BaseObservable {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("addressInfo")
    @Expose
    private AddressInfo addressInfo;
    @SerializedName("brandId")
    @Expose
    private Integer brandId;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("contactNo")
    @Expose
    private String contactNo;
    @SerializedName("divisionId")
    @Expose
    private Integer divisionId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gstn")
    @Expose
    private String gstn;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("name")
    @Expose
    private String name;

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

    public ServiceCenter() {
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyChange();
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
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

    @Bindable
    public String getGstn() {
        return gstn;
    }

    public void setGstn(String gstn) {
        this.gstn = gstn;
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


    public Pair<String, Integer> validateServiceInfo(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 7; i++) {
                fieldId = validateServiceFields(i, true);
                if (fieldId != VALIDATION_SUCCESS) {
                    tag = i + "";
                    break;
                }
            }
        } else {
            fieldId = validateServiceFields(Integer.parseInt(tag), false);
        }

        return new Pair<>(tag, fieldId);
    }

    private int validateServiceFields(int id, boolean emptyValidation) {
        switch (id) {
            case 0:
                if (emptyValidation && TextUtils.isEmpty(getName())) {
                    return AppConstants.RegistrationValidation.NAME_REQ;
                }
                break;

            case 1:
                boolean phoneEmpty = TextUtils.isEmpty(getContactNo());
                if (emptyValidation && phoneEmpty) {
                    return AppConstants.RegistrationValidation.PHONE_REQ;
                } else if (!phoneEmpty && !ValidationUtils.isPhoneNumberValid(
                        getContactNo())) {
                    return AppConstants.RegistrationValidation.PHONE_MIN_DIGITS;
                }
                break;

            case 2:
                boolean serviceCategory = TextUtils.isEmpty(getCategoryName());
                if (emptyValidation && serviceCategory) {
                    return AppConstants.RegistrationValidation.CATEGORY_REQ;
                }
                break;

            case 3:
                boolean serviceDivision = TextUtils.isEmpty(getDivisionName());
                if (emptyValidation && serviceDivision) {
                    return AppConstants.RegistrationValidation.DIVISION_REQ;
                }
                break;

            case 4:
                boolean serviceBrand = TextUtils.isEmpty(getBrandName());
                if (emptyValidation && serviceBrand) {
                    return AppConstants.RegistrationValidation.BRAND_REQ;
                }
                break;

            case 5:
                boolean serviceAddress = TextUtils.isEmpty(getAddress());
                if (emptyValidation && serviceAddress) {
                    return AppConstants.RegistrationValidation.ADDRESS_REQ;
                }
                break;


            case 6:
                boolean serviceEmail = TextUtils.isEmpty(getEmail());
                if (emptyValidation && serviceEmail) {
                    return AppConstants.RegistrationValidation.EMAIL_REQ;
                } else if (!serviceEmail && !ValidationUtils.isValidEmail(getEmail())) {
                    return AppConstants.RegistrationValidation.EMAIL_NOTVALID;
                }
                break;

            case 7:
                boolean serviceGstn = TextUtils.isEmpty(getGstn());
                if (emptyValidation && serviceGstn) {
                    return AppConstants.RegistrationValidation.GSTN_REQ;
                }
                break;

            default:
                return VALIDATION_SUCCESS;
        }
        return VALIDATION_SUCCESS;
    }
}
