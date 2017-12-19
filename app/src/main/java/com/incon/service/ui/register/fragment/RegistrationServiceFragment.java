package com.incon.service.ui.register.fragment;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.incon.service.AppConstants;
import com.incon.service.R;
import com.incon.service.apimodel.components.defaults.CategoryResponse;
import com.incon.service.apimodel.components.defaults.DefaultsResponse;
import com.incon.service.apimodel.components.fetchcategorie.Brand;
import com.incon.service.apimodel.components.fetchcategorie.Division;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppCheckBoxListDialog;
import com.incon.service.custom.view.AppOtpDialog;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.databinding.FragmentRegistrationServiceBinding;
import com.incon.service.dto.dialog.CheckedModelSpinner;
import com.incon.service.dto.registration.AddressInfo;
import com.incon.service.dto.registration.Registration;
import com.incon.service.dto.registration.ServiceCenter;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.notifications.PushPresenter;
import com.incon.service.ui.register.RegistrationActivity;
import com.incon.service.ui.termsandcondition.TermsAndConditionActivity;
import com.incon.service.utils.OfflineDataManager;
import com.incon.service.utils.SharedPrefsUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created on 13 Jun 2017 4:01 PM.
 */
public class RegistrationServiceFragment extends BaseFragment implements
        RegistrationServiceContract.View {
    private static final String TAG = RegistrationServiceFragment.class.getSimpleName();
    private FragmentRegistrationServiceBinding binding;
    private RegistrationServicePresenter registrationServiceFragmentPresenter;
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
    private List<FetchCategories> fetchCategorieList;
    private int categorySelectedPos = -1;
    private int divisionSelectedPos = -1;

    @Override
    protected void initializePresenter() {

        registrationServiceFragmentPresenter = new RegistrationServicePresenter();
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
        //defaultsResponse.getCategories()
        categoryResponseList = defaultsResponse.getCategories();
        for (int i = 0; i < categoryResponseList.size(); i++) {
            CheckedModelSpinner checkedModelSpinner = new CheckedModelSpinner();
            checkedModelSpinner.setName(categoryResponseList.get(i).getName());
            categorySpinnerList.add(checkedModelSpinner);
        }
       // loadCategoriesList(categorySpinnerList);
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
                    serviceCenter.setAddressInfo((AddressInfo) data.getParcelableExtra(IntentConstants.ADDRESS_INFO));
                    break;
                default:
                    break;
            }
        }

    }

    private void callRegisterApi() {

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

    @Override
    public void loadCategoriesList(List<FetchCategories> categoriesList) {
        fetchCategorieList = categoriesList;
        loadCategorySpinnerData();
    }


    private void loadCategorySpinnerData() {

        String[] stringCategoryList = new String[fetchCategorieList.size()];
        for (int i = 0; i < fetchCategorieList.size(); i++) {
            stringCategoryList[i] = fetchCategorieList.get(i).getName();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, stringCategoryList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerCategory.setAdapter(arrayAdapter);
        binding.spinnerCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (categorySelectedPos != position) {
                    FetchCategories fetchCategories = fetchCategorieList.get(position);
                    serviceCenter.setCategoryId(fetchCategories.getId());
                    serviceCenter.setCategoryName(fetchCategories.getName());
                    loadDivisionSpinnerData(fetchCategories.getDivisions());
                    binding.spinnerDivision.setText("");
                    categorySelectedPos = position;
                    binding.spinnerBrand.setVisibility(View.GONE);
                }

                //For avoiding double tapping issue
                if (binding.spinnerCategory.getOnItemClickListener() != null) {
                    binding.spinnerCategory.onItemClick(parent, view, position, id);
                }
            }
        });

    }

    private void loadDivisionSpinnerData(List<Division> divisions) {

        if (divisions.size() == 0) {
            binding.spinnerDivision.setVisibility(View.GONE);
            return;
        }

        binding.spinnerDivision.setVisibility(View.VISIBLE);
        String[] stringDivisionList = new String[divisions.size()];
        for (int i = 0; i < divisions.size(); i++) {
            stringDivisionList[i] = divisions.get(i).getName();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, stringDivisionList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerDivision.setAdapter(arrayAdapter);
        binding.spinnerDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (divisionSelectedPos != position) {
                    divisionSelectedPos = position;
                    FetchCategories fetchCategories = fetchCategorieList.get(categorySelectedPos);
                    Division divisions1 = fetchCategories.getDivisions().get(divisionSelectedPos);
                    serviceCenter.setDivisionId(divisions1.getId());
                    serviceCenter.setDivisionName(divisions1.getName());
                    loadBrandSpinnerData(divisions1.getBrands());
                    binding.spinnerBrand.setText("");
                }

                //For avoiding double tapping issue
                if (binding.spinnerDivision.getOnItemClickListener() != null) {
                    binding.spinnerDivision.onItemClick(parent, view, position, id);
                }
            }
        });
    }

    private void loadBrandSpinnerData(final List<Brand> brandList) {
        if (brandList.size() == 0) {
            binding.spinnerBrand.setVisibility(View.GONE);
            return;
        }
        binding.spinnerBrand.setVisibility(View.VISIBLE);
        String[] stringDivisionList = new String[brandList.size()];
        for (int i = 0; i < brandList.size(); i++) {
            stringDivisionList[i] = brandList.get(i).getName();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.view_spinner, stringDivisionList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerBrand.setAdapter(arrayAdapter);
        binding.spinnerBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                serviceCenter.setBrandId(brandList.get(position).getId());
                serviceCenter.setBrandName(brandList.get(position).getName());
                //For avoiding double tapping issue
                if (binding.spinnerBrand.getOnItemClickListener() != null) {
                    binding.spinnerBrand.onItemClick(parent, view, position, id);
                }
            }
        });
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        registrationServiceFragmentPresenter.disposeAll();
    }
}
