package com.incon.service.ui.resetpassword;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.incon.service.R;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppOtpDialog;
import com.incon.service.databinding.ActivityResetPasswordPromptBinding;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.changepassword.ChangePasswordActivity;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.HashMap;

public class ResetPasswordPromptActivity extends BaseActivity implements
        ResetPasswordPromptContract.View {

    private static final String TAG = ResetPasswordPromptActivity.class.getName();
    private ActivityResetPasswordPromptBinding binding;
    private ResetPasswordPromptPresenter resetPasswordPromptPresenter;
    private AppOtpDialog dialog;
    private String enteredOtp;
    private String phoneNumber;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_reset_password_prompt;
    }

    @Override
    protected void initializePresenter() {
        resetPasswordPromptPresenter = new ResetPasswordPromptPresenter();
        resetPasswordPromptPresenter.setView(this);
        setBasePresenter(resetPasswordPromptPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setResetPasswordPrompt(this);
        phoneNumber = getIntent().getStringExtra(IntentConstants.USER_PHONE_NUMBER);


        //make it as registration done but not verified otp
        SharedPrefsUtils.loginProvider().setBooleanPreference(LoginPrefs.IS_FORGOT_PASSWORD, true);
        SharedPrefsUtils.loginProvider().setStringPreference(LoginPrefs.USER_PHONE_NUMBER,
                phoneNumber);
        showOtpDialog();
    }

    // otp dialog
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
                                verifyOTP.put(ApiRequestKeyConstants.BODY_MOBILE_NUMBER,
                                        phoneNumber);
                                verifyOTP.put(ApiRequestKeyConstants.BODY_OTP, enteredOtp);
                                resetPasswordPromptPresenter.doRequestOtpApi(verifyOTP);

                                break;
                            case AlertDialogCallback.CANCEL:
                                dialog.dismiss();
                                ResetPasswordPromptActivity.this.finish();
                                break;
                            case TextAlertDialogCallback.RESEND_OTP:
//                                TODO have to implement resend otp
                                resetPasswordPromptPresenter.doResendOtpApi(phoneNumber);
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.dialog_verify_title, phoneNumber))
                .build();
        dialog.showDialog();
    }

    public void onOkClick() {
        //DO nothing
    }

    @Override
    public void validateOtp() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        intent.putExtra(IntentConstants.FROM_FORGOT_PASSWORD_SCREEN, true);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        resetPasswordPromptPresenter.disposeAll();
    }

}
