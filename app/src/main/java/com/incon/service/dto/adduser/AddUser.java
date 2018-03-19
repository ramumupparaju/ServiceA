package com.incon.service.dto.adduser;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.AppConstants;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.utils.DateUtils;
import com.incon.service.utils.ValidationUtils;

import java.util.Calendar;

import static com.incon.service.AppConstants.RegistrationValidation.DOB_PERSON_LIMIT;
import static com.incon.service.AppConstants.VALIDATION_SUCCESS;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public class AddUser extends BaseObservable implements Parcelable {


    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("serviceCenter")
    @Expose
    private AddServiceCenter serviceCenterResponse;
    @SerializedName("serviceCenterRoleId")
    @Expose
    private Integer serviceCenterRoleId;
    @SerializedName("reportingId")
    @Expose
    private Integer reportingId;

    private transient String serviceCenterName;
    private transient String serviceCenterDesignation;
    private transient String reportingName;


    private transient String genderType;

    private transient String confirmPassword;
    private transient String dateOfBirthToShow;

    public AddUser() {
    }

    @Bindable
    public String getReportingName() {
        return reportingName;
    }

    public void setReportingName(String reportingName) {
        this.reportingName = reportingName;
        notifyChange();
    }

    @Bindable
    public String getServiceCenterName() {
        return serviceCenterName;
    }

    public void setServiceCenterName(String serviceCenterName) {
        this.serviceCenterName = serviceCenterName;
        notifyChange();
    }
    @Bindable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        notifyChange();
    }

    @Bindable
    public String getServiceCenterDesignation() {
        return serviceCenterDesignation;
    }

    public void setServiceCenterDesignation(String serviceCenterDesignation) {
        this.serviceCenterDesignation = serviceCenterDesignation;
        notifyChange();
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

    @Bindable
    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
        notifyChange();
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyChange();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Bindable
    public String getGenderType() {
        return genderType;
    }

    public void setGenderType(String genderType) {
        this.genderType = genderType;
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

    public AddServiceCenter getServiceCenterResponse() {
        return serviceCenterResponse;
    }

    public void setServiceCenterResponse(AddServiceCenter serviceCenterResponse) {
        this.serviceCenterResponse = serviceCenterResponse;
    }

    public Integer getServiceCenterRoleId() {
        return serviceCenterRoleId;
    }

    public void setServiceCenterRoleId(Integer serviceCenterRoleId) {
        this.serviceCenterRoleId = serviceCenterRoleId;
    }

    public Integer getReportingId() {
        return reportingId;
    }

    public void setReportingId(Integer reportingId) {
        this.reportingId = reportingId;
    }

    private int validateDob() {
        Calendar dobDate = Calendar.getInstance();
        long dobInMillis = DateUtils.convertStringFormatToMillis(
                getDob(), AppConstants.DateFormatterConstants.YYYY_MM_DD_SLASH);
        dobDate.setTimeInMillis(dobInMillis);
        // futurde date check
        if (ValidationUtils.isFutureDate(dobDate)) {
            return AppConstants.RegistrationValidation.DOB_FUTURE_DATE;
        }

        int returnedYear = ValidationUtils.calculateAge(dobDate);
        if (returnedYear < AppConstants.AgeConstants.USER_DOB) {
            return DOB_PERSON_LIMIT;
        }
        return VALIDATION_SUCCESS;
    }

    public Pair<String, Integer> validateAddUser(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 9; i++) {
                fieldId = validateFields(i, true);
                if (fieldId != VALIDATION_SUCCESS) {
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
                    return AppConstants.AddUserValidations.NAME_REQ;
                }
                break;

            case 1:
                boolean numberEmpty = TextUtils.isEmpty(getMobileNumber());
                if (emptyValidation && numberEmpty) {
                    return AppConstants.AddUserValidations.PHONE_REQ;
                } else if (!numberEmpty && !ValidationUtils.isPhoneNumberValid(getMobileNumber())) {
                    return AppConstants.AddUserValidations.PHONE_MIN_DIGITS;
                }
                break;


            case 2:
                boolean genderEmpty = TextUtils.isEmpty(getGenderType());
                if (emptyValidation && genderEmpty) {
                    return AppConstants.AddUserValidations.GENDER_REQ;
                }
                break;

            /*case 3:
                boolean dobEmpty = TextUtils.isEmpty(getDob());
                if (emptyValidation && dobEmpty) {
                    return AppConstants.AddUserValidations.DOB_REQ;
                } else if (!dobEmpty) {
                    return validateDob();
                }
                break;*/

            case 4:
                boolean emailEmpty = TextUtils.isEmpty(getEmail());
                if (emptyValidation && emailEmpty) {
                    return AppConstants.AddUserValidations.EMAIL_REQ;
                } else if (!emailEmpty && !ValidationUtils.isValidEmail(getEmail())) {
                    return AppConstants.AddUserValidations.EMAIL_NOTVALID;
                }

                break;

            case 5:
                boolean passwordEmpty = TextUtils.isEmpty(getPassword());
                if (emptyValidation && passwordEmpty) {
                    return AppConstants.AddUserValidations.PASSWORD_REQ;
                } else if (!passwordEmpty && !ValidationUtils
                        .isPasswordValid(getPassword())) {
                    return AppConstants.AddUserValidations.PASSWORD_PATTERN_REQ;
                }
                break;

            case 6:
                boolean reEnterPasswordEmpty = TextUtils.isEmpty(getConfirmPassword());
                if (emptyValidation && reEnterPasswordEmpty) {
                    return AppConstants.AddUserValidations.RE_ENTER_PASSWORD_REQ;
                } else if (!reEnterPasswordEmpty) {
                    boolean passwordEmpty1 = TextUtils.isEmpty(getPassword());
                    if (passwordEmpty1 || (!getPassword()
                            .equals(getConfirmPassword()))) {
                        return AppConstants.AddUserValidations
                                .RE_ENTER_PASSWORD_DOES_NOT_MATCH;
                    }
                }
                break;

            case 7:
                boolean addressEmpty = TextUtils.isEmpty(getAddress());
                if (emptyValidation && addressEmpty) {
                    return AppConstants.AddUserValidations.ADDRESS_REQ;
                }
                break;

            case 8:
                boolean serviceCenterDesignationEmpty = TextUtils.isEmpty(getServiceCenterDesignation());
                if (emptyValidation && serviceCenterDesignationEmpty) {
                    return AppConstants.AddUserValidations.SERVICE_CENTER_DESIGNATION;
                }
                break;

            case 9:
                boolean repotingPersonEmpty = TextUtils.isEmpty(getReportingName());
                if (emptyValidation && repotingPersonEmpty) {
                    return AppConstants.AddUserValidations.REPORTING_PERSON;
                }
                break;

            default:
                return VALIDATION_SUCCESS;
        }
        return VALIDATION_SUCCESS;
    }


    protected AddUser(Parcel in) {
        address = in.readString();
        country = in.readString();
        dob = in.readString();
        email = in.readString();
        gender = in.readString();
        location = in.readString();
        mobileNumber = in.readString();
        name = in.readString();
        password = in.readString();
        serviceCenterResponse = (AddServiceCenter) in.readValue(AddServiceCenter.class.getClassLoader());
        serviceCenterRoleId = in.readByte() == 0x00 ? null : in.readInt();
        reportingId = in.readByte() == 0x00 ? null : in.readInt();
        serviceCenterName = in.readString();
        serviceCenterDesignation = in.readString();
        genderType = in.readString();
        confirmPassword = in.readString();
        dateOfBirthToShow = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(country);
        dest.writeString(dob);
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(location);
        dest.writeString(mobileNumber);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeValue(serviceCenterResponse);
        if (serviceCenterRoleId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(serviceCenterRoleId);
        }
        if (reportingId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(reportingId);
        }
        dest.writeString(serviceCenterName);
        dest.writeString(serviceCenterDesignation);
        dest.writeString(genderType);
        dest.writeString(confirmPassword);
        dest.writeString(dateOfBirthToShow);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<AddUser> CREATOR = new Parcelable.Creator<AddUser>() {
        @Override
        public AddUser createFromParcel(Parcel in) {
            return new AddUser(in);
        }

        @Override
        public AddUser[] newArray(int size) {
            return new AddUser[size];
        }
    };
}