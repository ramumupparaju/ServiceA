package com.incon.service.ui.settings.update;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.custom.view.CustomAutoCompleteView;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.databinding.ActivityUpdateUserProfileBinding;
import com.incon.service.dto.update.UpDateUserProfile;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.utils.DateUtils;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import static com.incon.service.AppConstants.IntentConstants.USER_PHONE_NUMBER;
import static com.incon.service.AppConstants.LoginPrefs.USER_ADDRESS;
import static com.incon.service.AppConstants.LoginPrefs.USER_DOB;
import static com.incon.service.AppConstants.LoginPrefs.USER_EMAIL_ID;
import static com.incon.service.AppConstants.LoginPrefs.USER_GENDER;
import static com.incon.service.AppConstants.LoginPrefs.USER_ID;
import static com.incon.service.AppConstants.LoginPrefs.USER_NAME;


/**
 * Created by PC on 10/13/2017.
 */
public class UpDateUserProfileActivity extends BaseActivity implements
        UpDateUserProfileContract.View {
    private ActivityUpdateUserProfileBinding binding;
    private UpDateUserProfilePresenter upDateUserProfilePresenter;
    private UpDateUserProfile upDateUserProfile;
    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_user_profile;
    }

    @Override
    protected void initializePresenter() {
        upDateUserProfilePresenter = new UpDateUserProfilePresenter();
        upDateUserProfilePresenter.setView(this);
        setBasePresenter(upDateUserProfilePresenter);
    }

    private void enableEditMode(boolean isEditable) {
        binding.setIsEditable(isEditable);
        binding.spinnerGender.setDropDownHeight(
                isEditable ? LinearLayout.LayoutParams.WRAP_CONTENT : 0);
    }

    public void onSubmitClick() {
        if (validateFields()) {
            upDateUserProfilePresenter.upDateUserProfile(SharedPrefsUtils.loginProvider().
                    getIntegerPreference(USER_ID, DEFAULT_VALUE), upDateUserProfile);
        }
    }

    public void onAddressClick() {
        Intent addressIntent = new Intent(this, RegistrationMapActivity.class);
        startActivityForResult(addressIntent, RequestCodes.ADDRESS_LOCATION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.ADDRESS_LOCATION:
                    upDateUserProfile.setAddress(data.getStringExtra(
                            IntentConstants.ADDRESS_COMMA));
                    upDateUserProfile.setLocation(data.getStringExtra(
                            IntentConstants.LOCATION_COMMA));
                    break;
                default:
                    break;
            }
        }
    }

    public void onDobClick() {
        showDatePicker();
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setUpDateUserProfileActivity(this);
        upDateUserProfile = new UpDateUserProfile();
        binding.setUpDateUserProfile(upDateUserProfile);
        enableEditMode(false);
        initializeToolbar();
        loadData();
        initViews();
    }

    private void initViews() {

        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        loadValidationErrors();
        setFocusForViews();
    }

    private void initializeToolbar() {
        binding.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.toolbarEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditMode(true);
            }
        });
    }

    private void loadData() {
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        loadUserData();
        loadValidationErrors();
        loadGenderSpinnerData();
    }

    private void loadUserData() {
        SharedPrefsUtils sharedPrefsUtils = SharedPrefsUtils.loginProvider();

        upDateUserProfile.setName(sharedPrefsUtils.getStringPreference(USER_NAME));

        upDateUserProfile.setMobileNumber(sharedPrefsUtils.getStringPreference(
                USER_PHONE_NUMBER));

        upDateUserProfile.setGender(sharedPrefsUtils.getStringPreference(
                USER_GENDER));

        upDateUserProfile.setDob(sharedPrefsUtils.getStringPreference(
                USER_DOB));

        upDateUserProfile.setUserEmail(sharedPrefsUtils.getStringPreference(
                USER_EMAIL_ID));

        upDateUserProfile.setAddress(sharedPrefsUtils.getStringPreference(
                USER_ADDRESS));

    }

    private void showDatePicker() {

        AppUtils.hideSoftKeyboard(this, binding.viewDob);
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        String dateOfBirth = upDateUserProfile.getDateOfBirthToShow();
        if (!TextUtils.isEmpty(dateOfBirth)) {
            cal.setTimeInMillis(DateUtils.convertStringFormatToMillis(
                    dateOfBirth, DateFormatterConstants.MM_DD_YYYY));
        }

        int customStyle = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? R.style.DatePickerDialogTheme : android.R.style.Theme_DeviceDefault_Light_Dialog;

        DatePickerDialog datePicker = new DatePickerDialog(this,
                customStyle,
                datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.show();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {
                // when dialog box is closed, below method will be called.
                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    Calendar selectedDateTime = Calendar.getInstance();
                    selectedDateTime.set(selectedYear, selectedMonth, selectedDay);

                    String dobInMMDDYYYY = DateUtils.convertDateToOtherFormat(
                            selectedDateTime.getTime(), DateFormatterConstants.MM_DD_YYYY);
                    upDateUserProfile.setDateOfBirthToShow(dobInMMDDYYYY);

                }
            };

    void loadGenderSpinnerData() {
        String[] genderTypeList = getResources().getStringArray(R.array.gender_options_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.view_spinner, genderTypeList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerGender.setAdapter(arrayAdapter);
    }


    private boolean validateFields() {
        binding.inputLayoutUpDateUserName.setError(null);
        binding.inputLayoutUpDatePhone.setError(null);
        binding.spinnerGender.setError(null);
        binding.inputLayoutUpDateDob.setError(null);
        binding.inputLayoutUpDateEmailid.setError(null);
        binding.inputLayoutUpDateAddress.setError(null);

        Pair<String, Integer> validation = binding.getUpDateUserProfile().
                validateUpDateUserProfile(null);
        updateUiAfterValidation(validation.first, validation.second);
        return validation.second == VALIDATION_SUCCESS;
    }


    private void setFocusForViews() {
        binding.edittextUpDateUserName.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextUpDatePhone.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextUpDateDob.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextUpDateEmailid.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextUpDateAddress.setOnFocusChangeListener(onFocusChangeListener);
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            Object fieldId = view.getTag();
            if (fieldId != null) {
                Pair<String, Integer> validation = binding.getUpDateUserProfile().
                        validateUpDateUserProfile((String) fieldId);
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
        } else if (view instanceof CustomAutoCompleteView) {
            ((CustomTextInputLayout) view.getParent().getParent())
                    .setError(validationId == VALIDATION_SUCCESS ? null
                            : errorMap.get(validationId));
        }

        if (validationId != VALIDATION_SUCCESS) {
            view.startAnimation(shakeAnim);
        }
    }

    // validations
    private void loadValidationErrors() {

        errorMap = new HashMap<>();
        errorMap.put(RegistrationValidation.NAME_REQ,
                getString(R.string.error_name_req));

        errorMap.put(RegistrationValidation.PHONE_REQ,
                getString(R.string.error_phone_req));

        errorMap.put(RegistrationValidation.PHONE_MIN_DIGITS,
                getString(R.string.error_phone_min_digits));

        errorMap.put(RegistrationValidation.GENDER_REQ,
                getString(R.string.error_gender_req));

        errorMap.put(RegistrationValidation.DOB_REQ,
                getString(R.string.error_dob_req));

        errorMap.put(RegistrationValidation.DOB_FUTURE_DATE,
                getString(R.string.error_dob_futuredate));

        errorMap.put(RegistrationValidation.DOB_PERSON_LIMIT,
                getString(R.string.error_dob_patient_is_user));

        errorMap.put(RegistrationValidation.EMAIL_REQ,
                getString(R.string.error_email_req));

        errorMap.put(RegistrationValidation.EMAIL_NOTVALID,
                getString(R.string.error_email_notvalid));

        errorMap.put(RegistrationValidation.PASSWORD_REQ,
                getString(R.string.error_password_req));

        errorMap.put(RegistrationValidation.PASSWORD_PATTERN_REQ,
                getString(R.string.error_password_pattern_req));

        errorMap.put(RegistrationValidation.RE_ENTER_PASSWORD_REQ,
                getString(R.string.error_re_enter_password_req));

        errorMap.put(RegistrationValidation.RE_ENTER_PASSWORD_DOES_NOT_MATCH,
                getString(R.string.error_re_enter_password_does_not_match));

        errorMap.put(RegistrationValidation.ADDRESS_REQ, getString(R.string.error_address_req));


    }

    @Override
    public void loadUpDateUserProfileResponce(LoginResponse loginResponse) {
        enableEditMode(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        upDateUserProfilePresenter.disposeAll();
    }
}
