package com.incon.service.ui.addservicecenter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.TextView;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.databinding.ActivityAddserviceCenterBinding;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.utils.DateUtils;
import com.incon.service.utils.SharedPrefsUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public class AddServiceCenterActivity extends BaseActivity implements
        AddServiceCenterContract.View {
    private AddServiceCenterPresenter addServiceCenterPresenter;
    private ActivityAddserviceCenterBinding binding;
    private AddServiceCenter addServiceCenter;
    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;

    @Override
    protected void initializePresenter() {
        addServiceCenterPresenter = new AddServiceCenterPresenter();
        addServiceCenterPresenter.setView(this);
        setBasePresenter(addServiceCenterPresenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addservice_center;
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());

        Intent bundle = getIntent();
        if (bundle != null)
            addServiceCenter = bundle.getParcelableExtra(IntentConstants.SERVICE_CENTER_DATA);
        if (addServiceCenter != null) {
            binding.toolbarTitle.setText(getString(R.string.title_update_service_center));
            binding.buttonSubmit.setText(getString(R.string.action_update));
            binding.toolbarDeleteIv.setVisibility(View.VISIBLE);
        } else {
            binding.toolbarTitle.setText(getString(R.string.action_add_service_center));
            binding.toolbarDeleteIv.setVisibility(View.GONE);
            binding.buttonSubmit.setText(getString(R.string.action_submit));
            addServiceCenter = new AddServiceCenter();
            // TODO have to check
            addServiceCenter.setCategoryId(-1);
            addServiceCenter.setDivisionId(-1);
            addServiceCenter.setBrandId(-1);
        }
        binding.setAddServiceCenter(addServiceCenter);
        binding.setAddServiceCenterActivity(this);
        initViews();
        initializeToolbar();

    }

    private void initViews() {
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        loadValidationErrors();
        setFocusForViews();

        addServiceCenterPresenter.defaultsApi();
    }

    private void initializeToolbar() {
        binding.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.toolbarDeleteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteServiceCenterDialog();
            }
        });
    }

    private void showDeleteServiceCenterDialog() {

    }

    public void onDateClick() {
        showDatePicker();
    }

    private void showDatePicker() {
        AppUtils.hideSoftKeyboard(this, binding.edittextCreatedDate);
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        String createdDate = addServiceCenter.getCreatedDate();
        if (!TextUtils.isEmpty(createdDate)) {
            cal.setTimeInMillis(DateUtils.convertStringFormatToMillis(
                    createdDate, DateFormatterConstants.FROM_API_MILLIS));
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
                    String yyyyMMDD = DateUtils.convertDateToOtherFormat(
                            selectedDateTime.getTime(), DateFormatterConstants.FROM_API_MILLIS);
                    addServiceCenter.setCreatedDate(yyyyMMDD);

                    Pair<String, Integer> validate = binding.getAddServiceCenter().
                            validateAddServiceCenter((String) binding.edittextCreatedDate.getTag());
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
                    addServiceCenter.setAddress(data.getStringExtra(IntentConstants.ADDRESS_COMMA));
                    addServiceCenter.setLocation(data.getStringExtra(IntentConstants.LOCATION_COMMA));
                    break;
                default:
                    break;
            }
        }

    }

    private void setFocusForViews() {
        TextView.OnEditorActionListener onEditorActionListener =
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            switch (textView.getId()) {
                                case R.id.edittext_number:
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
        binding.edittextCreatedDate.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterGstn.setOnFocusChangeListener(onFocusChangeListener);
    }


    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            Object fieldId = view.getTag();
            if (fieldId != null) {
                Pair<String, Integer> validation = addServiceCenter.
                        validateAddServiceCenter((String) fieldId);
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
        binding.inputLayoutName.setError(null);
        binding.inputLayoutNumber.setError(null);
        binding.inputLayoutRegisterEmailid.setError(null);
        binding.inputLayoutRegisterAddress.setError(null);
        binding.inputLayoutCreatedDate.setError(null);
        binding.inputLayoutRegisterGstn.setError(null);

        Pair<String, Integer> validation = binding.getAddServiceCenter().validateAddServiceCenter(null);
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
        } else {
            ((MaterialBetterSpinner) view).setError(validationId == VALIDATION_SUCCESS ? null
                    : errorMap.get(validationId));
        }

        if (validationId != VALIDATION_SUCCESS) {
            view.startAnimation(shakeAnim);
        }
    }

    private void loadValidationErrors() {
        errorMap = new HashMap<>();
        errorMap.put(AddServiceCenterValidation.NAME_REQ,
                getString(R.string.error_name_req));

        errorMap.put(AddServiceCenterValidation.PHONE_REQ,
                getString(R.string.error_phone_req));

        errorMap.put(AddServiceCenterValidation.PHONE_MIN_DIGITS,
                getString(R.string.error_phone_min_digits));

        errorMap.put(AddServiceCenterValidation.GENDER_REQ,
                getString(R.string.error_gender_req));

        errorMap.put(AddServiceCenterValidation.CREATED_DATE_REQ,
                getString(R.string.error_created_date_req));

        errorMap.put(AddServiceCenterValidation.CREATED_FUTURE_DATE,
                getString(R.string.error_dob_futuredate));


        errorMap.put(AddServiceCenterValidation.EMAIL_REQ,
                getString(R.string.error_email_req));

        errorMap.put(AddServiceCenterValidation.EMAIL_NOTVALID,
                getString(R.string.error_email_notvalid));

        errorMap.put(AddServiceCenterValidation.ADDRESS_REQ, getString(R.string.error_address_req));

        errorMap.put(AddServiceCenterValidation.DIVISION_REQ, getString(R.string.error_division_req));

        errorMap.put(AddServiceCenterValidation.BRAND_REQ, getString(R.string.error_brand_req));

        errorMap.put(AddServiceCenterValidation.CATEGORY_REQ, getString(R.string.error_category_req));

        errorMap.put(AddServiceCenterValidation.GSTN_REQ, getString(R.string.error_gstn_req));

    }

    public void onSubmitClick() {
        if (validateFields()) {
            addServiceCenterPresenter.addingServiceCenter(SharedPrefsUtils.loginProvider().
                    getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE), addServiceCenter);

        }
    }

    @Override
    public void loadedDefaultsData(boolean isDataAvailable) {

        if (isDataAvailable) {

            //TODO have to initialize spinner data from application class list
        } else {
            AppUtils.shortToast(this, getString(R.string.error_loading_categories));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addServiceCenterPresenter.disposeAll();
    }


}
