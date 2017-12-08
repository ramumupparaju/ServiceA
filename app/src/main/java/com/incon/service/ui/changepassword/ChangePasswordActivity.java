package com.incon.service.ui.changepassword;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.incon.service.AppConstants;
import com.incon.service.R;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.databinding.ActivityChangePasswordBinding;
import com.incon.service.dto.changepassword.Password;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.HashMap;



public class ChangePasswordActivity extends BaseActivity implements ChangePasswordContract.View {
    private static final String TAG = ChangePasswordActivity.class.getName();
    private ChangePasswordPresenter changePasswordPresenter;
    private ActivityChangePasswordBinding binding;

    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;
    private boolean isFromForgotPasswordScreen;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void initializePresenter() {
        changePasswordPresenter = new ChangePasswordPresenter();
        changePasswordPresenter.setView(this);
        setBasePresenter(changePasswordPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        Password password = new Password();
        binding.setPassword(password);
        binding.setActivity(this);

        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        isFromForgotPasswordScreen = getIntent().getBooleanExtra(IntentConstants
                .FROM_FORGOT_PASSWORD_SCREEN, false);
        loadValidationErrors();
        setFocusForViews();
    }

    /**
     * button click called from xml
     */
    public void onChangePasswordClick() {
        if (validateFields()) {
            HashMap<String, String> passwordMap = new HashMap<>();
            passwordMap.put(ApiRequestKeyConstants.BODY_USER_ID,
                    SharedPrefsUtils.loginProvider().getStringPreference(
                            AppConstants.LoginPrefs.USER_PHONE_NUMBER));
            passwordMap.put(AppConstants.ApiRequestKeyConstants.BODY_PASSWORD,
                    binding.getPassword().getConfirmPassword());
            changePasswordPresenter.changePassword(passwordMap);
        }
    }

    @Override
    public void navigateToLoginPage(LoginResponse loginResponse) {
        if (isFromForgotPasswordScreen) { //if user comes to this screen using settings from
            // toolbar simply finish else if user comes from forgotpassword screen navigate to
            // home screen
            Intent intent = new Intent(this,
                    HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent
                    .FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        finish();
    }

    private void loadValidationErrors() {
        errorMap = new HashMap<>();
        errorMap.put(AppConstants.PasswordValidation.NEWPWD_REQ, getString(R.string.error_newpwd_req));

        errorMap.put(AppConstants.PasswordValidation.NEWPWD_PATTERN_REQ,
                getString(R.string.error_password_pattern_req));

        errorMap.put(AppConstants.PasswordValidation.CONFIRMPWD_REQ,
                getString(R.string.error_confirmpwd_req));

        errorMap.put(AppConstants.PasswordValidation.PWD_NOTMATCH,
                getString(R.string.error_pwd_notmatched));
    }

    private void setFocusForViews() {
        binding.edittextEnterNewpassword.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextConfirmPassword.setOnFocusChangeListener(onFocusChangeListener);
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            Object fieldId = view.getTag();
            if (fieldId != null) {
                Pair<String, Integer> validation = binding.getPassword().
                        validateUserInfo((String) fieldId);
                if (!hasFocus) {
                    if (view instanceof TextInputEditText) {
                        TextInputEditText textInputEditText = (TextInputEditText) view;
                        textInputEditText.setText(textInputEditText.getText().toString().trim());
                    }
                    updateUiAfterValidation(validation.first, validation.second);
                }
            }
        }
    };


    private boolean validateFields() {
        binding.inputLayoutEnterNewpassword.setError(null);
        binding.inputLayoutConfirmPassword.setError(null);

        Pair<String, Integer> validation = binding.getPassword().validateUserInfo(null);
        updateUiAfterValidation(validation.first, validation.second);

        return validation.second == VALIDATION_SUCCESS;
    }

    private void updateUiAfterValidation(String tag, int validationId) {
        if (tag == null) {
            return;
        }
        View viewByTag = binding.getRoot().findViewWithTag(tag);
        setFieldError(viewByTag, validationId);
    }

    private void setFieldError(View view, int validationId) {

        if (view instanceof TextInputEditText) {
            ((CustomTextInputLayout) view.getParent().getParent())
                    .setError(validationId == VALIDATION_SUCCESS ? null
                            : errorMap.get(validationId));
        }

        if (validationId != VALIDATION_SUCCESS) {
            view.startAnimation(shakeAnim);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        changePasswordPresenter.disposeAll();
    }
}
