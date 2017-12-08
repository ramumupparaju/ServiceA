package com.incon.service.dto.login;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.AppConstants;

public class LoginUserData extends BaseObservable implements AppConstants {


    @SerializedName("userId")
    @Expose
    private String phoneNumber;
    private String password;

    public LoginUserData(String email, String password) {
        this.phoneNumber = email;
        this.password = password;
    }

    public LoginUserData() {
    }

    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int validateLogin() {
        int isValid = AppConstants.VALIDATION_SUCCESS;
        if (TextUtils.isEmpty(phoneNumber)) {
            isValid = LoginValidation.PHONE_NUMBER_REQ;
        } else if (phoneNumber.length() < 10) {
            isValid = LoginValidation.PHONE_NUMBER_NOTVALID;
        } else if (TextUtils.isEmpty(password)) {
            isValid = LoginValidation.PASSWORD_REQ;
        }
        return isValid;
    }
}
