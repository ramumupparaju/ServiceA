package com.incon.service.dto.changepassword;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Pair;

import com.incon.service.AppConstants;
import com.incon.service.utils.ValidationUtils;


public class Password  extends BaseObservable implements AppConstants {

    private String newPassword;
    private String confirmPassword;

    @Bindable
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Bindable
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Pair<String, Integer> validateUserInfo(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 1; i++) {
                fieldId = validateFields(i, true);
                if (fieldId != AppConstants.VALIDATION_SUCCESS) {
                    tag = i + "";
                    break;
                }
            }
        }
        else {
            fieldId =  validateFields(Integer.parseInt(tag), false);
        }

        return  new Pair<>(tag, fieldId);
    }

    private int validateFields(int id, boolean emptyValidation) {
        switch (id) {
            case 0:
                boolean passwordEmpty = TextUtils.isEmpty(getNewPassword());
                if (emptyValidation && passwordEmpty) {
                    return PasswordValidation.NEWPWD_REQ;
                }
                else if (!passwordEmpty && !ValidationUtils
                        .isPasswordValid(getNewPassword())) {
                    return PasswordValidation.NEWPWD_PATTERN_REQ;
                }
                break;

            case 1:
                boolean reEnterPasswordEmpty = TextUtils.isEmpty(getConfirmPassword());
                if (emptyValidation && reEnterPasswordEmpty) {
                    return PasswordValidation.CONFIRMPWD_REQ;
                }
                else if (!reEnterPasswordEmpty) {
                    boolean passwordEmpty1 = TextUtils.isEmpty(getNewPassword());
                    if (passwordEmpty1 || (!getNewPassword()
                            .equals(getConfirmPassword()))) {
                        return PasswordValidation
                                .PWD_NOTMATCH;
                    }

                }
                break;

            default:
                return AppConstants.VALIDATION_SUCCESS;
        }
        return AppConstants.VALIDATION_SUCCESS;
    }

}
