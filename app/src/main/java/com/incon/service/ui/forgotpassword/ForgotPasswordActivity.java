package com.incon.service.ui.forgotpassword;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.incon.service.R;
import com.incon.service.databinding.ActivityForgotPasswordBinding;
import com.incon.service.databinding.ViewRegistrationBottomButtonsBinding;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.resetpassword.ResetPasswordPromptActivity;
import com.incon.service.utils.ValidationUtils;

import java.util.HashMap;

public class ForgotPasswordActivity extends BaseActivity implements ForgotPasswordContract.View,
        View.OnClickListener {

    private static final String TAG = ForgotPasswordActivity.class.getName();
    private ForgotPasswordPresenter forgotPasswordPresenter;
    private ActivityForgotPasswordBinding binding;

    private ViewRegistrationBottomButtonsBinding buttonsBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void initializePresenter() {
        forgotPasswordPresenter = new ForgotPasswordPresenter();
        forgotPasswordPresenter.setView(this);
        setBasePresenter(forgotPasswordPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        buttonsBinding = binding.includeRegisterBottomButtons;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String phoneNumber = bundle.getString(IntentConstants.USER_PHONE_NUMBER);
            if (phoneNumber != null && phoneNumber.length() > 0) {
                binding.edittextUsername.setText(phoneNumber);
                binding.edittextUsername.setSelection(phoneNumber.length());
            }
        }

        setBottomButtonViews();
        setButtonClickListeners();
    }

    @Override
    public void navigateToResetPromtPage() {
        Intent registrationIntent = new Intent(this, ResetPasswordPromptActivity.class);
        registrationIntent.putExtra(IntentConstants.USER_PHONE_NUMBER, binding.edittextUsername
                .getText().toString());
        startActivity(registrationIntent);
        finish();
    }

    @Override
    public boolean validatePhoneNumber() {
        boolean isValid = true;
        binding.inputLayoutUsername.setError(null);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        String phoneNumber = binding.edittextUsername.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            binding.inputLayoutUsername.setError(getString(R.string.error_phone_req));
            binding.inputLayoutUsername.startAnimation(shake);
            isValid = false;
        } else if (!ValidationUtils.isPhoneNumberValid(phoneNumber)) {
            binding.inputLayoutUsername.setError(getString(R.string.error_phone_min_digits));
            binding.inputLayoutUsername.startAnimation(shake);
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_left:
                finish();
                break;
            case R.id.button_right:
                boolean validPhoneNumber = validatePhoneNumber();
                if (validPhoneNumber) {
                    HashMap<String, String> emailMap = new HashMap<>();
                    emailMap.put(ApiRequestKeyConstants.BODY_USER_ID,
                            binding.edittextUsername.getText().toString());
                    forgotPasswordPresenter.forgotPassword(emailMap);
                }
                break;
            default:
                break;
        }
    }

    private void setBottomButtonViews() {
        buttonsBinding.buttonLeft.setText(R.string.action_back);
        buttonsBinding.buttonRight.setText(R.string.action_reset_password);
    }

    private void setButtonClickListeners() {
        buttonsBinding.buttonLeft.setOnClickListener(this);
        buttonsBinding.buttonRight.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        forgotPasswordPresenter.disposeAll();
    }

}
