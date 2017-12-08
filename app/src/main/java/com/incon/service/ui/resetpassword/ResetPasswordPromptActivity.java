package com.incon.service.ui.resetpassword;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.incon.service.AppConstants;
import com.incon.service.R;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppOtpDialog;
import com.incon.service.databinding.ActivityResetPasswordPromptBinding;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.changepassword.ChangePasswordActivity;
import com.incon.service.ui.register.fragment.RegistrationUserFragmentContract;
import com.incon.service.ui.register.fragment.RegistrationUserFragmentPresenter;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.HashMap;

public class ResetPasswordPromptActivity extends BaseActivity implements
        RegistrationUserFragmentContract.View {

    private static final String TAG = ResetPasswordPromptActivity.class.getName();
    private ActivityResetPasswordPromptBinding binding;
    private AppOtpDialog dialog;
    private String enteredOtp;
    private String phoneNumber;
    private RegistrationUserFragmentPresenter registrationUserFragmentPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password_prompt;
    }

    @Override
    protected void initializePresenter() {
        registrationUserFragmentPresenter = new RegistrationUserFragmentPresenter();
        registrationUserFragmentPresenter.setView(this);
        setBasePresenter(registrationUserFragmentPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setResetPasswordPrompt(this);
        phoneNumber = getIntent().getStringExtra(AppConstants.IntentConstants.USER_PHONE_NUMBER);


        //make it as registration done but not verified otp
        SharedPrefsUtils.loginProvider().setBooleanPreference(AppConstants.LoginPrefs.IS_FORGOT_PASSWORD, true);
        SharedPrefsUtils.loginProvider().setStringPreference(AppConstants.LoginPrefs.USER_PHONE_NUMBER,
                phoneNumber);
        showOtpDialog();
    }

    private void showOtpDialog() {
        dialog = new AppOtpDialog.AlertDialogBuilder(ResetPasswordPromptActivity.this, new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String otpString) {
                        enteredOtp = otpString;
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                if (TextUtils.isEmpty(enteredOtp)) {
                                    showErrorMessage(getString(R.string.error_otp_req));
                                    return;
                                }
                                HashMap<String, String> verifyOTP = new HashMap<>();
                                verifyOTP.put(AppConstants.ApiRequestKeyConstants.BODY_MOBILE_NUMBER,
                                        phoneNumber);
                                verifyOTP.put(AppConstants.ApiRequestKeyConstants.BODY_OTP, enteredOtp);
                                registrationUserFragmentPresenter.validateOTP(verifyOTP);

                                break;
                            case AlertDialogCallback.CANCEL:
                                dialog.dismiss();
                                ResetPasswordPromptActivity.this.finish();
                                break;
                            case TextAlertDialogCallback.RESEND_OTP:
                                registrationUserFragmentPresenter.registerRequestPasswordOtp(
                                       phoneNumber);
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.dialog_verify_title, phoneNumber))
                .build();
        dialog.showDialog();
    }


    @Override
    public void showProgress(String message) {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
    }

    public void onOkClick() {
        //DO nothing
    }

    @Override
    public void navigateToRegistrationActivityNext() {
        //DO nothing
    }

    @Override
    public void navigateToHomeScreen() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Intent intent = new Intent(this,
                ChangePasswordActivity.class);
        intent.putExtra(AppConstants.IntentConstants.FROM_FORGOT_PASSWORD_SCREEN, true);
        startActivity(intent);
        finish();

    }

    @Override
    public void validateOTP() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        registrationUserFragmentPresenter.disposeAll();
    }
}
