package com.incon.service.dto.registration;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.AppConstants;
import com.incon.service.utils.DateUtils;
import com.incon.service.utils.ValidationUtils;

import java.util.Calendar;

import static com.incon.service.AppConstants.RegistrationValidation.DOB_FUTURE_DATE;
import static com.incon.service.AppConstants.RegistrationValidation.DOB_PERSON_LIMIT;
import static com.incon.service.AppConstants.VALIDATION_SUCCESS;


public class Registration extends BaseObservable {


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
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("serviceCenterUserType")
    @Expose
    private String serviceCenterUserType;

    @SerializedName("serviceCenter")
    @Expose
    private ServiceCenter serviceCenter;

    private transient String genderType;

    private transient String confirmPassword;
    private transient String dateOfBirthToShow;
    @Bindable
    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
        notifyChange();
    }


    public ServiceCenter getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(ServiceCenter serviceCenter) {
        this.serviceCenter = serviceCenter;
    }
    @Bindable
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    @Bindable
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
        notifyChange();
    }

    public String getServiceCenterUserType() {
        return serviceCenterUserType;
    }

    public void setServiceCenterUserType(String serviceCenterUserType) {
        this.serviceCenterUserType = serviceCenterUserType;
    }

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        notifyChange();
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyChange();
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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


    public Pair<String, Integer> validateUserInfo(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 7; i++) {
                fieldId = validateUserFields(i, true);
                if (fieldId != VALIDATION_SUCCESS) {
                    tag = i + "";
                    break;
                }
            }
        } else {
            fieldId = validateUserFields(Integer.parseInt(tag), false);
        }

        return new Pair<>(tag, fieldId);
    }

    private int validateUserFields(int id, boolean emptyValidation) {
        switch (id) {
            case 0:
                if (emptyValidation && TextUtils.isEmpty(getName())) {
                    return AppConstants.RegistrationValidation.NAME_REQ;
                }
                break;

            case 1:
                boolean phoneEmpty = TextUtils.isEmpty(getMobileNumber());
                if (emptyValidation && phoneEmpty) {
                    return AppConstants.RegistrationValidation.PHONE_REQ;
                } else if (!phoneEmpty && !ValidationUtils.isPhoneNumberValid(getMobileNumber())) {
                    return AppConstants.RegistrationValidation.PHONE_MIN_DIGITS;
                }
                break;

            case 2:
                boolean genderTypeEmpty = TextUtils.isEmpty(getGender());
                if (emptyValidation && genderTypeEmpty) {
                    return AppConstants.RegistrationValidation.GENDER_REQ;
                }
                break;

            case 3:
                boolean dobEmpty = TextUtils.isEmpty(getDob());
                if (emptyValidation && dobEmpty) {
                    return AppConstants.RegistrationValidation.DOB_REQ;
                } else if (!dobEmpty) {
                    return validateDob();
                }
                break;

            case 4:
                boolean emailEmpty = TextUtils.isEmpty(getEmail());
                if (emptyValidation && emailEmpty) {
                    return AppConstants.RegistrationValidation.EMAIL_REQ;
                } else if (!emailEmpty && !ValidationUtils.isValidEmail(getEmail())) {
                    return AppConstants.RegistrationValidation.EMAIL_NOTVALID;
                }
                break;

            case 5:
                boolean passwordEmpty = TextUtils.isEmpty(getPassword());
                if (emptyValidation && passwordEmpty) {
                    return AppConstants.RegistrationValidation.PASSWORD_REQ;
                } else if (!passwordEmpty && !ValidationUtils
                        .isPasswordValid(getPassword())) {
                    return AppConstants.RegistrationValidation.PASSWORD_PATTERN_REQ;
                }
                break;

            case 6:
                boolean reEnterPasswordEmpty = TextUtils.isEmpty(getConfirmPassword());
                if (emptyValidation && reEnterPasswordEmpty) {
                    return AppConstants.RegistrationValidation.RE_ENTER_PASSWORD_REQ;
                } else if (!reEnterPasswordEmpty) {
                    boolean passwordEmpty1 = TextUtils.isEmpty(getPassword());
                    if (passwordEmpty1 || (!getPassword()
                            .equals(getConfirmPassword()))) {
                        return AppConstants.RegistrationValidation
                                .RE_ENTER_PASSWORD_DOES_NOT_MATCH;
                    }

                }
                break;

            case 7:
                boolean userAddress = TextUtils.isEmpty(getGender());
                if (emptyValidation && userAddress) {
                    return AppConstants.RegistrationValidation.ADDRESS_REQ;
                }
                break;

            default:
                return VALIDATION_SUCCESS;
        }
        return VALIDATION_SUCCESS;
    }

    private int validateDob() {
        Calendar dobDate = Calendar.getInstance();
        long dobInMillis = DateUtils.convertStringFormatToMillis(
                getDob(), AppConstants.DateFormatterConstants.YYYY_MM_DD_SLASH);
        dobDate.setTimeInMillis(dobInMillis);
        // futurde date check
        if (ValidationUtils.isFutureDate(dobDate)) {
            return DOB_FUTURE_DATE;
        }

        int returnedYear = ValidationUtils.calculateAge(dobDate);
        if (returnedYear < AppConstants.AgeConstants.USER_DOB) {
            return DOB_PERSON_LIMIT;
        }
        return VALIDATION_SUCCESS;
    }




}
