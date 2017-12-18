package com.incon.service.ui.register.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.incon.service.AppConstants;
import com.incon.service.R;
import com.incon.service.apimodel.components.defaults.CategoryResponse;
import com.incon.service.apimodel.components.defaults.DefaultsResponse;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppCheckBoxListDialog;
import com.incon.service.custom.view.AppOtpDialog;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.databinding.FragmentRegistrationServiceBinding;
import com.incon.service.dto.dialog.CheckedModelSpinner;
import com.incon.service.dto.registration.Registration;
import com.incon.service.dto.registration.ServiceCenter;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.notifications.PushPresenter;
import com.incon.service.ui.register.RegistrationActivity;
import com.incon.service.ui.termsandcondition.TermsAndConditionActivity;
import com.incon.service.utils.Logger;
import com.incon.service.utils.OfflineDataManager;
import com.incon.service.utils.PermissionUtils;
import com.incon.service.utils.SharedPrefsUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.android.gms.internal.zzs.TAG;


/**
 * Created on 13 Jun 2017 4:01 PM.
 */
public class RegistrationServiceFragment extends BaseFragment implements
        RegistrationServiceFragmentContract.View {
    private static final String TAG = RegistrationServiceFragment.class.getSimpleName();
    private FragmentRegistrationServiceBinding binding;
    private RegistrationServiceFragmentPresenter registrationServiceFragmentPresenter;
    private List<CategoryResponse> categoryResponseList; //fetched from defaults api call in
    // registration
    private Registration register; // initialized from registration acticity
    private ServiceCenter serviceCenter;

    private Animation shakeAnim;
    private HashMap<Integer, String> errorMap;
    private String selectedFilePath = "";
    private AppOtpDialog dialog;
    private AppCheckBoxListDialog categoryDialog;
    private List<CheckedModelSpinner> categorySpinnerList;
    private String enteredOtp;

    @Override
    protected void initializePresenter() {

        registrationServiceFragmentPresenter = new RegistrationServiceFragmentPresenter();
        registrationServiceFragmentPresenter.setView(this);
        setBasePresenter(registrationServiceFragmentPresenter);
    }

    @Override
    public void setTitle() {
        // do nothing
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_registration_service, container, false);
        binding.setServiceFragment(this);
        //here data must be an instance of the registration class
        serviceCenter = new ServiceCenter();
        binding.setServiceCenter(serviceCenter);
        View rootView = binding.getRoot();

        loadData();
        setTitle();
        return rootView;
    }

    private void loadData() {
        shakeAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        loadValidationErrors();
        setFocusListenersForEditText();


        categorySpinnerList = new ArrayList<>();
        DefaultsResponse defaultsResponse = new OfflineDataManager().loadData(
                DefaultsResponse.class, DefaultsResponse.class.getName());
        categoryResponseList = defaultsResponse.getCategories();
        for (int i = 0; i < categoryResponseList.size(); i++) {
            CheckedModelSpinner checkedModelSpinner = new CheckedModelSpinner();
            checkedModelSpinner.setName(categoryResponseList.get(i).getName());
            categorySpinnerList.add(checkedModelSpinner);
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

        errorMap.put(RegistrationValidation.CATEGORY_REQ,
                getString(R.string.error_category_req));

        errorMap.put(RegistrationValidation.DIVISION_REQ,
                getString(R.string.error_division_req));

        errorMap.put(RegistrationValidation.BRAND_REQ,
                getString(R.string.error_brand_req));

        errorMap.put(RegistrationValidation.ADDRESS_REQ,
                getString(R.string.error_address_req));

        errorMap.put(RegistrationValidation.EMAIL_REQ,
                getString(R.string.error_email_req));

        errorMap.put(RegistrationValidation.EMAIL_NOTVALID,
                getString(R.string.error_email_notvalid));

        errorMap.put(RegistrationValidation.GSTN_REQ,
                getString(R.string.error_gstn_req));
    }


    private void setFocusListenersForEditText() {

        TextView.OnEditorActionListener onEditorActionListener =
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            switch (textView.getId()) {
                                case R.id.edittext_register_phone:
                                    binding.edittextRegisterCategory.requestFocus();
                                    showCategorySelectionDialog();
                                    break;

                                default:
                            }
                        }
                        return true;
                    }
                };

        binding.edittextRegisterPhone.setOnEditorActionListener(onEditorActionListener);

        binding.edittextRegisterServiceName.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterPhone.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextRegisterEmailid.setOnFocusChangeListener(onFocusChangeListener);
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {

            Object fieldId = view.getTag();
            if (fieldId != null) {
                Pair<String, Integer> validation = serviceCenter.validateServiceInfo((String) fieldId);
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
        } else {
            ((MaterialBetterSpinner) view).setError(validationId == VALIDATION_SUCCESS ? null
                    : errorMap.get(validationId));
        }

        if (validationId != VALIDATION_SUCCESS) {
            view.startAnimation(shakeAnim);
            ((RegistrationActivity) getActivity()).focusOnView(binding.scrollviewServiceInfo, view);
        }
    }

    public void onAddressClick() {
        Intent addressIntent = new Intent(getActivity(), RegistrationMapActivity.class);
        startActivityForResult(addressIntent, RequestCodes.ADDRESS_LOCATION);
    }

    /**
     * called from registration activity when user click on next in bottom bars
     * validate user , if all fields ok then call next screen
     */
    public void onClickNext() {
        if (validateFields()) {

            navigateToRegistrationActivityNext();
        }
    }

    private boolean validateFields() {
        binding.inputLayoutRegisterServiceName.setError(null);
        binding.inputLayoutRegisterPhone.setError(null);
        binding.inputLayoutRegisterEmailid.setError(null);
//        binding.spinnerCategory.setError(null);

        Pair<String, Integer> validation = serviceCenter.validateServiceInfo(null);
        updateUiAfterValidation(validation.first, validation.second);

        return validation.second == VALIDATION_SUCCESS;
    }

    @Override
    public void navigateToRegistrationActivityNext() {
        Intent eulaIntent = new Intent(getActivity(), TermsAndConditionActivity.class);
        startActivityForResult(eulaIntent, AppConstants.RequestCodes.TERMS_AND_CONDITIONS);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TERMS_AND_CONDITIONS:
                    callRegisterApi();
                    break;
                case RequestCodes.ADDRESS_LOCATION:
                    serviceCenter.setAddress(data.getStringExtra(IntentConstants.ADDRESS_COMMA));
                    serviceCenter.setLocation(data.getStringExtra(IntentConstants.LOCATION_COMMA));
                    break;
                default:
                    break;
            }
        }

    }

    private void callRegisterApi() {
        //sets category ids as per api requirement
        String[] categoryNames = serviceCenter.getName().split(
                COMMA_SEPARATOR);
        StringBuilder stringBuilder = new StringBuilder();
        for (String categoryName : categoryNames) {
            CategoryResponse categoryResponse = new CategoryResponse();
            categoryResponse.setName(categoryName);
            int indexOf = categoryResponseList.indexOf(categoryResponse);
            stringBuilder.append(categoryResponseList.get(indexOf).getId());
            stringBuilder.append(AppConstants.COMMA_SEPARATOR);
        }
        int start = stringBuilder.length() - 1;
        serviceCenter.setCategoryId(stringBuilder.toString().substring(0, start));

        //sets gender type as single char as per api requirement

        registrationServiceFragmentPresenter.register(register);
    }

    public void navigateToHomeScreen() {
        PushPresenter pushPresenter = new PushPresenter();
        pushPresenter.pushRegisterApi();

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Intent intent = new Intent(getActivity(),
                HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent
                .FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    //validate otp
    @Override
    public void validateOTP() {
        //make it as registration done but not verified otp
        SharedPrefsUtils.loginProvider().setBooleanPreference(LoginPrefs.IS_REGISTERED, true);
        SharedPrefsUtils.loginProvider().setStringPreference(LoginPrefs.USER_PHONE_NUMBER,
                serviceCenter.getContactNo());

        showOtpDialog();
    }

    // otp dialog
    private void showOtpDialog() {
        final String phoneNumber = serviceCenter.getContactNo();
        dialog = new AppOtpDialog.AlertDialogBuilder(getActivity(), new
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
                                registrationServiceFragmentPresenter.validateOTP(verifyOTP);
                                break;
                            case AlertDialogCallback.CANCEL:
                                dialog.dismiss();
                                break;
                            case TextAlertDialogCallback.RESEND_OTP:
                                registrationServiceFragmentPresenter.registerRequestOtp(phoneNumber);
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.dialog_verify_title, phoneNumber))
                .build();
        dialog.showDialog();
    }

    public void onCategoryClick() {
        showCategorySelectionDialog();
    }

    //  category selection dialog
    private void showCategorySelectionDialog() {
        //set previous selected categories as checked
        String selectedCategories = binding.edittextRegisterCategory.getText().toString();
        if (!TextUtils.isEmpty(selectedCategories)) {
            String[] split = selectedCategories.split(COMMA_SEPARATOR);
            for (String categoryString : split) {
                CheckedModelSpinner checkedModelSpinner = new CheckedModelSpinner();
                checkedModelSpinner.setName(categoryString);
                int indexOf = categorySpinnerList.indexOf(checkedModelSpinner);
                categorySpinnerList.get(indexOf).setChecked(true);
            }

        }
        categoryDialog = new AppCheckBoxListDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String caetogories) {
                        binding.edittextRegisterCategory.setText(caetogories);
                        binding.edittextRegisterDivision.setText(caetogories);
                        binding.edittextRegisterBrand.setText(caetogories);
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                categoryDialog.dismiss();
                                break;
                            case AlertDialogCallback.CANCEL:
                                categoryDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.register_category_hint))
                .spinnerItems(categorySpinnerList)
                .build();
        categoryDialog.showDialog();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        registrationServiceFragmentPresenter.disposeAll();
    }
}
