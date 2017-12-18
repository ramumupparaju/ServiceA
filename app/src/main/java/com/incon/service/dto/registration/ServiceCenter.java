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
    private String brandId;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("contactNo")
    @Expose
    private String contactNo;
    @SerializedName("divisionId")
    @Expose
    private String divisionId;
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

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGstn() {
        return gstn;
    }

    public void setGstn(String gstn) {
        this.gstn = gstn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                boolean serviceCategory = TextUtils.isEmpty(getCategoryId());
                if (emptyValidation && serviceCategory) {
                    return AppConstants.RegistrationValidation.CATEGORY_REQ;
                }
                break;

            case 3:
                boolean serviceDivision = TextUtils.isEmpty(getDivisionId());
                if (emptyValidation && serviceDivision) {
                    return AppConstants.RegistrationValidation.DIVISION_REQ;
                }
                break;

            case 4:
                boolean serviceBrand = TextUtils.isEmpty(getBrandId());
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
                }else if (!serviceEmail && !ValidationUtils.isValidEmail(getEmail())) {
                    return AppConstants.RegistrationValidation.EMAIL_NOTVALID;
                }
                break;

            case 7:
                boolean serviceGstn = TextUtils.isEmpty(getEmail());
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
