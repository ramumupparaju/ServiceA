package com.incon.service.dto.update;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.AppConstants;
import com.incon.service.utils.DateUtils;

/**
 * Created by PC on 10/13/2017.
 */

public class UpDateUserProfile  extends BaseObservable {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("name")
    @Expose
    private String name;

    public  UpDateUserProfile() {
    }

    private transient String dateOfBirthToShow;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyChange();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Bindable
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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
    public String getDateOfBirthToShow() {
        return dateOfBirthToShow;
    }

    public void setDateOfBirthToShow(String dateOfBirthToShow) {
        this.dateOfBirthToShow = dateOfBirthToShow;
        dob = DateUtils.convertDateToAnotherFormat(dateOfBirthToShow, AppConstants
                .DateFormatterConstants.MM_DD_YYYY, AppConstants.DateFormatterConstants
                .MM_DD_YYYY);
        notifyChange();
    }

    public Pair<String, Integer> validateUpDateUserProfile(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 5; i++) {
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
                boolean nameEmpty = TextUtils.isEmpty(name);
                if (emptyValidation && nameEmpty) {
                    return AppConstants.RegistrationValidation.NAME_REQ;
                }
                break;

            case 1:
                boolean mobileNumberEmpty = TextUtils.isEmpty(mobileNumber);
                if (emptyValidation && mobileNumberEmpty) {
                    return AppConstants.RegistrationValidation.PHONE_REQ;
                }
                break;

            case 2:
                boolean genderEmpty = TextUtils.isEmpty(gender);
                if (emptyValidation && genderEmpty) {
                    return AppConstants.RegistrationValidation.GENDER_REQ;
                }
                break;

            case 3:
                boolean dobEmpty = TextUtils.isEmpty(dateOfBirthToShow);
                if (emptyValidation && dobEmpty) {
                    return AppConstants.RegistrationValidation.DOB_REQ;
                }
                break;
            case 4:
                boolean userEmailEmpty = TextUtils.isEmpty(email);
                if (emptyValidation && userEmailEmpty) {
                    return AppConstants.RegistrationValidation.EMAIL_REQ;
                }
                break;


            case 5:
                boolean addressEmpty = TextUtils.isEmpty(address);
                if (emptyValidation && addressEmpty) {
                    return AppConstants.RegistrationValidation.ADDRESS_REQ;
                }
                break;

            default:
                return AppConstants.VALIDATION_SUCCESS;
        }
        return AppConstants.VALIDATION_SUCCESS;
    }

}
