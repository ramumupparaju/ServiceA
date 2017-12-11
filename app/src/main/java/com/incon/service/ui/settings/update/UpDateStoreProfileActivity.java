package com.incon.service.ui.settings.update;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.incon.service.R;
import com.incon.service.apimodel.components.defaults.CategoryResponse;
import com.incon.service.apimodel.components.defaults.DefaultsResponse;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppCheckBoxListDialog;
import com.incon.service.custom.view.CustomAutoCompleteView;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.custom.view.PickImageDialog;
import com.incon.service.custom.view.PickImageDialogInterface;
import com.incon.service.databinding.ActivityUpdateStoreProfileBinding;
import com.incon.service.dto.dialog.CheckedModelSpinner;
import com.incon.service.dto.update.UpDateStoreProfile;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.utils.Logger;
import com.incon.service.utils.OfflineDataManager;
import com.incon.service.utils.PermissionUtils;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.incon.service.AppConstants.ApiRequestKeyConstants.STORE_LOGO;
import static com.incon.service.AppConstants.LoginPrefs.STORE_ADDRESS;
import static com.incon.service.AppConstants.LoginPrefs.STORE_CATEGORY_NAME;
import static com.incon.service.AppConstants.LoginPrefs.STORE_EMAIL_ID;
import static com.incon.service.AppConstants.LoginPrefs.STORE_GSTN;
import static com.incon.service.AppConstants.LoginPrefs.STORE_ID;
import static com.incon.service.AppConstants.LoginPrefs.STORE_NAME;
import static com.incon.service.AppConstants.LoginPrefs.STORE_PHONE_NUMBER;


/**
 * Created by PC on 10/13/2017.
 */

public class UpDateStoreProfileActivity extends BaseActivity implements
        UpDateStoreProfileContract.View {
    private ActivityUpdateStoreProfileBinding binding;
    private PickImageDialog pickImageDialog;
    private AppCheckBoxListDialog categoryDialog;
    private String selectedFilePath = "";
    private List<CheckedModelSpinner> categorySpinnerList;
    private List<CategoryResponse> categoryResponseList;
    private UpDateStoreProfile upDateStoreProfile;
    private UpDateStoreProfilePresenter upDateStoreProfilePresenter;
    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;
    private static final String TAG = UpDateStoreProfileActivity.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_store_profile;
    }

    @Override
    protected void initializePresenter() {
        upDateStoreProfilePresenter = new UpDateStoreProfilePresenter();
        upDateStoreProfilePresenter.setView(this);
        setBasePresenter(upDateStoreProfilePresenter);
    }
    private void enableEditMode(boolean isEditable) {
        binding.setIsEditable(isEditable);
    }
    public void onSubmitClick() {
        if (validateFields()) {

            if (TextUtils.isEmpty(selectedFilePath)) {
                showErrorMessage(getString(R.string.error_image_path_upload));
                return;
            }
            upDateStoreProfilePresenter.upDateStoreProfile(SharedPrefsUtils.loginProvider().
                    getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE), upDateStoreProfile);
        }
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setUpDateStoreProfileActivity(this);
        upDateStoreProfile = new UpDateStoreProfile();
        binding.setUpDateStoreProfile(upDateStoreProfile);
        binding.setIsEditable(false);
        loadData();
        initializeToolbar();
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
        loadStoreData();
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
    private void loadStoreData() {
        SharedPrefsUtils sharedPrefsUtils = SharedPrefsUtils.loginProvider();
        upDateStoreProfile.setStoreId(sharedPrefsUtils.getIntegerPreference(
                STORE_ID, DEFAULT_VALUE));
        upDateStoreProfile.setStoreName(sharedPrefsUtils.getStringPreference(STORE_NAME));
        upDateStoreProfile.setContactNumber(sharedPrefsUtils.getStringPreference(
                STORE_PHONE_NUMBER));
        upDateStoreProfile.setStoreCategoryNames(sharedPrefsUtils.getStringPreference(
                STORE_CATEGORY_NAME));
        upDateStoreProfile.setStoreAddress(sharedPrefsUtils.getStringPreference(
                STORE_ADDRESS));
        upDateStoreProfile.setStoreEmail(sharedPrefsUtils.getStringPreference(
                STORE_EMAIL_ID));
        upDateStoreProfile.setGstn(sharedPrefsUtils.getStringPreference(
                STORE_GSTN));
        upDateStoreProfile.setLogo(sharedPrefsUtils.getStringPreference(
                STORE_LOGO));
    }

    public void openCameraToUpload() {
        PermissionUtils.getInstance().grantPermission(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                new PermissionUtils.Callback() {
                    @Override
                    public void onFinish(HashMap<String, Integer> permissionsStatusMap) {
                        int storageStatus = permissionsStatusMap.get(
                                Manifest.permission.CAMERA);
                        switch (storageStatus) {
                            case PermissionUtils.PERMISSION_GRANTED:
                                showImageOptionsDialog();
                                Logger.v(TAG, "location :" + "granted");
                                break;
                            case PermissionUtils.PERMISSION_DENIED:
                                Logger.v(TAG, "location :" + "denied");
                                break;
                            case PermissionUtils.PERMISSION_DENIED_FOREVER:
                                Logger.v(TAG, "location :" + "denied forever");
                                break;
                            default:
                                break;
                        }
                    }
                });
    }

    public void onAddressClick() {
        Intent addressIntent = new Intent(this, RegistrationMapActivity.class);
        startActivityForResult(addressIntent, RequestCodes.ADDRESS_LOCATION);
    }
    private void showImageOptionsDialog() {
        pickImageDialog = new PickImageDialog(this);
        pickImageDialog.mImageHandlingDelegate = pickImageDialogInterface;
        pickImageDialog.initDialogLayout();
    }

    public void onCategoryClick() {
        showCategorySelectionDialog();
    }

    private void showCategorySelectionDialog() {
        //set previous selected categories as checked
        String selectedCategories = binding.edittextUpDateCategory.getText().toString();
        if (!TextUtils.isEmpty(selectedCategories)) {
            String[] split = selectedCategories.split(COMMA_SEPARATOR);
            for (String categoryString : split) {
                CheckedModelSpinner checkedModelSpinner = new CheckedModelSpinner();
                checkedModelSpinner.setName(categoryString);
                int indexOf = categorySpinnerList.indexOf(checkedModelSpinner);
                categorySpinnerList.get(indexOf).setChecked(true);
            }

        }
        categoryDialog = new AppCheckBoxListDialog.AlertDialogBuilder(this, new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String caetogories) {
                        binding.edittextUpDateCategory.setText(caetogories);
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

    private PickImageDialogInterface pickImageDialogInterface = new PickImageDialogInterface() {
        @Override
        public void handleIntent(Intent intent, int requestCode) {
            if (requestCode == RequestCodes.SEND_IMAGE_PATH) { // loading image in full screen
                if (TextUtils.isEmpty(selectedFilePath)) {
                    showErrorMessage(getString(R.string.error_image_path_req));
                } else {
                    intent.putExtra(IntentConstants.IMAGE_PATH, selectedFilePath);
                    startActivity(intent);
                }
                return;
            }
            startActivityForResult(intent, requestCode);
        }

        @Override
        public void displayPickedImage(String uri, int requestCode) {
            selectedFilePath = uri;
            ((BaseActivity) UpDateStoreProfileActivity.this).loadImageUsingGlide(
                    selectedFilePath, binding.storeLogoIv);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TERMS_AND_CONDITIONS:
                    //TODO need to cal api cal
                    //callRegisterApi();
                    break;
                case RequestCodes.ADDRESS_LOCATION:
                    upDateStoreProfile.setStoreAddress(data.getStringExtra(
                            IntentConstants.ADDRESS_COMMA));
                    upDateStoreProfile.setStoreLocation(data.getStringExtra(
                            IntentConstants.LOCATION_COMMA));
                    break;
                default:
                    pickImageDialog.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }

    }
    private boolean validateFields() {
        binding.inputLayoutUpDateStoreName.setError(null);
        binding.inputLayoutUpDatePhone.setError(null);
        binding.inputLayoutUpDateCategory.setError(null);
        binding.inputLayoutUpDateAddress.setError(null);
        binding.inputLayoutUpDateEmailid.setError(null);
        binding.inputLayoutUpDateGstn.setError(null);

        Pair<String, Integer> validation = binding.getUpDateStoreProfile().
                validateUpDateStoreProfile(null);
        updateUiAfterValidation(validation.first, validation.second);
        return validation.second == VALIDATION_SUCCESS;
    }

    private void setFocusForViews() {
        binding.edittextUpDateStoreName.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextUpDatePhone.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextUpDateCategory.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextUpDateAddress.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextUpDateEmailid.setOnFocusChangeListener(onFocusChangeListener);
        binding.edittextUpDateGstn.setOnFocusChangeListener(onFocusChangeListener);
    }

    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            Object fieldId = view.getTag();
            if (fieldId != null) {
                Pair<String, Integer> validation = binding.getUpDateStoreProfile().
                        validateUpDateStoreProfile((String) fieldId);
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
        errorMap.put(RegistrationValidation.NAME_REQ,
                getString(R.string.error_name_req));
        errorMap.put(RegistrationValidation.PHONE_REQ,
                getString(R.string.error_phone_req));
        errorMap.put(RegistrationValidation.PHONE_MIN_DIGITS,
                getString(R.string.error_phone_min_digits));
        errorMap.put(RegistrationValidation.CATEGORY_REQ,
                getString(R.string.error_category_req));
        errorMap.put(RegistrationValidation.ADDRESS_REQ, getString(R.string.error_address_req));
        errorMap.put(RegistrationValidation.EMAIL_REQ,
                getString(R.string.error_email_req));
        errorMap.put(RegistrationValidation.EMAIL_NOTVALID,
                getString(R.string.error_email_notvalid));
        errorMap.put(RegistrationValidation.GSTN_REQ, getString(R.string.error_gstn_req));
    }

    @Override
    public void loadUpDateStoreProfileResponce(LoginResponse loginResponse) {
        binding.setIsEditable(false);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        upDateStoreProfilePresenter.disposeAll();
    }
}



