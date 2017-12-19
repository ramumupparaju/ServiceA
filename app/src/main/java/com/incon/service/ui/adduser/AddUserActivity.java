package com.incon.service.ui.adduser;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.custom.view.CustomAutoCompleteView;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.databinding.ActivityAdduserBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.utils.DateUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public class AddUserActivity extends BaseActivity implements
        AddUserContract.View {
    private View rootView;
    private AddUserPresenter addUserPresenter;
    private AddUser addUser;
    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;
    private MaterialBetterSpinner genderSpinner;
    private ActivityAdduserBinding binding;

    @Override
    protected void initializePresenter() {

        addUserPresenter = new AddUserPresenter();
        addUserPresenter.setView(this);
        setBasePresenter(addUserPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adduser;
    }


   /* @Override
    public void setTitle() {

    }*/

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        addUser = new AddUser();
        binding.setAddUser(addUser);
        binding.setAddUserActivity(this);
        rootView = binding.getRoot();
        initViews();
    }

    private void initViews() {
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        loadGenderSpinnerData();
        loadValidationErrors();
        setFocusListenersForEditText();
    }

    private void loadGenderSpinnerData() {
        String[] genderTypeList = getResources().getStringArray(R.array.gender_options_list);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.view_spinner, genderTypeList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        genderSpinner = binding.spinnerGender;
        genderSpinner.setAdapter(arrayAdapter);

    }

    public void onDobClick() {
        showDatePicker();
    }

    private void showDatePicker() {
        AppUtils.hideSoftKeyboard(this, binding.viewDob);
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());

        String dateOfBirth = addUser.getDateOfBirthToShow();
        if (!TextUtils.isEmpty(dateOfBirth)) {
            cal.setTimeInMillis(DateUtils.convertStringFormatToMillis(
                    dateOfBirth, DateFormatterConstants.MM_DD_YYYY));
        }

        int customStyle = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
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
                    addUser.setDateOfBirthToShow(dobInMMDDYYYY);

                    Pair<String, Integer> validate = binding.getAddUser().
                            validateAddUser((String) binding.edittextRegisterDob.getTag());
                    updateUiAfterValidation(validate.first, validate.second);
                }
            };

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
                    addUser.setAddress(data.getStringExtra(IntentConstants.ADDRESS_COMMA));
                    addUser.setLocation(data.getStringExtra(IntentConstants.LOCATION_COMMA));
                    break;
                default:
                    break;
            }
        }

    }

    private void setFocusListenersForEditText() {
        TextView.OnEditorActionListener onEditorActionListener =
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            switch (textView.getId()) {
                                case R.id.edittext_register_phone:
                                    binding.spinnerGender.requestFocus();
                                    binding.spinnerGender.showDropDown();
                                    break;

                                default:
                            }
                        }
                        return true;
                    }
                };
        binding.edittextNumber.setOnEditorActionListener(onEditorActionListener);
        binding.edittextName.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextNumber.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterEmailid.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterAddress.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterPassword.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterReenterPassword.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterServiceCenterId.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterServiceCenterRoleId.setOnFocusChangeListener(onFocusChangeListener);
    }


    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            Object fieldId = view.getTag();
            if (fieldId != null) {
                Pair<String, Integer> validation = addUser.
                        validateAddUser((String) fieldId);
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

    private void loadValidationErrors() {
        errorMap = new HashMap<>();
        errorMap.put(AddUserValidations.NAME_REQ,
                getString(R.string.error_name_req));

        errorMap.put(AddUserValidations.PHONE_REQ,
                getString(R.string.error_phone_req));

        errorMap.put(AddUserValidations.PHONE_MIN_DIGITS,
                getString(R.string.error_phone_min_digits));

        errorMap.put(AddUserValidations.GENDER_REQ,
                getString(R.string.error_gender_req));

        errorMap.put(AddUserValidations.CREATED_DATE_REQ,
                getString(R.string.error_dob_req));

        errorMap.put(AddUserValidations.DOB_FUTURE_DATE,
                getString(R.string.error_dob_futuredate));

        errorMap.put(AddUserValidations.DOB_PERSON_LIMIT,
                getString(R.string.error_dob_patient_is_user));

        errorMap.put(AddUserValidations.EMAIL_REQ,
                getString(R.string.error_email_req));

        errorMap.put(AddUserValidations.EMAIL_NOTVALID,
                getString(R.string.error_email_notvalid));

        errorMap.put(AddUserValidations.PASSWORD_REQ,
                getString(R.string.error_password_req));

        errorMap.put(AddUserValidations.PASSWORD_PATTERN_REQ,
                getString(R.string.error_password_pattern_req));

        errorMap.put(AddUserValidations.RE_ENTER_PASSWORD_REQ,
                getString(R.string.error_re_enter_password_req));

        errorMap.put(AddUserValidations.RE_ENTER_PASSWORD_DOES_NOT_MATCH,
                getString(R.string.error_re_enter_password_does_not_match));

        errorMap.put(AddUserValidations.ADDRESS_REQ, getString(R.string.error_address_req));
        errorMap.put(AddUserValidations.SERVICE_CENTER_ID, getString(R.string.error_Service_Center_id));
        errorMap.put(AddUserValidations.SERVICE_CENTER_ROLE_ID, getString(R.string.error_service_center_role_id));

    }


    private boolean validateFields() {
        binding.inputLayoutName.setError(null);
        binding.inputLayoutNumber.setError(null);
        binding.inputLayoutRegisterEmailid.setError(null);
        binding.inputLayoutRegisterAddress.setError(null);
        binding.inputLayoutRegisterDob.setError(null);
        binding.spinnerGender.setError(null);
        binding.inputLayoutRegisterPassword.setError(null);
        binding.inputLayoutRegisterConfirmPassword.setError(null);
        binding.inputLayoutRegisterServiceCenterId.setError(null);
        binding.inputLayoutRegisterServiceCenterRoleId.setError(null);

        Pair<String, Integer> validation = binding.getAddUser().validateAddUser(null);
        updateUiAfterValidation(validation.first, validation.second);

        return validation.second == VALIDATION_SUCCESS;
    }

    public void onSubmitClick() {
        if (validateFields()) {
            //addUserPresenter.addingUser();
        }
    }
}
