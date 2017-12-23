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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.incon.service.AppConstants;
import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchcategorie.Brand;
import com.incon.service.apimodel.components.fetchcategorie.Division;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.custom.view.AppOtpDialog;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.custom.view.PickImageDialog;
import com.incon.service.custom.view.PickImageDialogInterface;
import com.incon.service.databinding.FragmentRegistrationServiceBinding;
import com.incon.service.dto.registration.AddressInfo;
import com.incon.service.dto.registration.Registration;
import com.incon.service.dto.registration.ServiceCenter;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.ui.notifications.PushPresenter;
import com.incon.service.ui.register.RegistrationActivity;
import com.incon.service.ui.termsandcondition.TermsAndConditionActivity;
import com.incon.service.utils.Logger;
import com.incon.service.utils.PermissionUtils;
import com.incon.service.utils.SharedPrefsUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import net.hockeyapp.android.LoginActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.incon.service.AppConstants.LoginPrefs.STORE_LOGO;


/**
 * Created on 13 Jun 2017 4:01 PM.
 */
public class RegistrationServiceFragment extends BaseFragment implements
        RegistrationServiceContract.View {
    private static final String TAG = RegistrationServiceFragment.class.getSimpleName();
    private FragmentRegistrationServiceBinding binding;
    private RegistrationServicePresenter registrationServicePresenter;
    // registration
    private Registration register; // initialized from registration acticity
    private ServiceCenter serviceCenter;
    private PickImageDialog pickImageDialog;
    private Animation shakeAnim;
    private HashMap<Integer, String> errorMap;
    private String selectedFilePath = "";
    private AppOtpDialog dialog;
    private String enteredOtp;
    private List<FetchCategories> fetchCategoryList;
    private int categorySelectedPos = -1;
    private int divisionSelectedPos = -1;
    private int brandSelectedPos = -1;

    @Override
    protected void initializePresenter() {
        registrationServicePresenter = new RegistrationServicePresenter();
        registrationServicePresenter.setView(this);
        setBasePresenter(registrationServicePresenter);
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
        register = ((RegistrationActivity) getActivity()).getRegistration();
        serviceCenter = new ServiceCenter();
        //TODO have to remove hard code
        serviceCenter.setName("shiva");
        serviceCenter.setContactNo("1234567890");
        serviceCenter.setEmail("asdj@g.com");
        serviceCenter.setGstn("12345sdv");


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

        fetchCategoryList = new ArrayList<>(ConnectApplication.getAppContext().getFetchCategoriesList());
        loadCategoriesList(fetchCategoryList);
    }

    public void loadCategoriesList(List<FetchCategories> categoriesList) {
        fetchCategoryList = categoriesList;
        loadCategorySpinnerData();
    }
    //  camera to open
    public void openCameraToUpload() {
        PermissionUtils.getInstance().grantPermission(getActivity(),
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

    private void showImageOptionsDialog() {
        pickImageDialog = new PickImageDialog(getActivity());
        pickImageDialog.mImageHandlingDelegate = pickImageDialogInterface;
        pickImageDialog.initDialogLayout();
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
            ((BaseActivity) getActivity()).loadImageUsingGlide(
                    selectedFilePath, binding.storeLogoIv);
        }
    };


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

            //TODO have to uncomment image validation code
           /* if (TextUtils.isEmpty(selectedFilePath)) {
                showErrorMessage(getString(R.string.error_image_path_upload));
                return;
            }*/

            //setting category id
            FetchCategories fetchCategories = fetchCategoryList.get(categorySelectedPos);
            serviceCenter.setCategoryId(fetchCategories.getId());

            //setting division id
            Division divisions = fetchCategories.getDivisions().get(divisionSelectedPos);
            serviceCenter.setDivisionId(divisions.getId());

            //setting brand id it it is not equal to -1
            serviceCenter.setBrandId(brandSelectedPos == -1 ? brandSelectedPos : divisions.getBrands().get(brandSelectedPos).getId());

            navigateToRegistrationActivityNext();
        }
    }

    private boolean validateFields() {
        binding.inputLayoutRegisterServiceName.setError(null);
        binding.inputLayoutRegisterPhone.setError(null);
        binding.spinnerCategory.setError(null);
        binding.spinnerDivision.setError(null);
        binding.inputLayoutRegisterAddress.setError(null);
        binding.inputLayoutRegisterEmailid.setError(null);
        binding.inputLayoutRegisterGstn.setError(null);

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
                    register.setServiceCenter(serviceCenter);
                    callRegisterApi();
                    break;
                case RequestCodes.ADDRESS_LOCATION:
                    serviceCenter.setAddress(data.getStringExtra(IntentConstants.ADDRESS_COMMA));
                    serviceCenter.setLocation(data.getStringExtra(IntentConstants.LOCATION_COMMA));
                    serviceCenter.setAddressInfo((AddressInfo) data.getParcelableExtra(IntentConstants.ADDRESS_INFO));
                    break;
                default:
                    pickImageDialog.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }

    }

    private void callRegisterApi() {
        registrationServicePresenter.register(register);
    }

    public void navigateToHomeScreen() {
        PushPresenter pushPresenter = new PushPresenter();
        pushPresenter.pushRegisterApi();

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Intent intent = new Intent(getActivity(),
                LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent
                .FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void uploadServiceCenterLogo(int serviceCenterId) {
        File fileToUpload = new File(selectedFilePath == null ? "" : selectedFilePath);
        if (fileToUpload.exists()) {
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), fileToUpload);
            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part imagenPerfil = MultipartBody.Part.createFormData(STORE_LOGO,
                    fileToUpload.getName(), requestFile);
            registrationServicePresenter.uploadServiceCenterLogo(serviceCenterId, imagenPerfil);
        } else {
            showErrorMessage(getString(R.string.error_image_path_upload));
        }

    }

    @Override
    public void navigateToLoginScreen() {
        Intent loginIntent = new Intent(getActivity(), com.incon.service.ui.login.LoginActivity
                .class);
        startActivity(loginIntent);

    }

    private void loadCategorySpinnerData() {

        String[] stringCategoryList = new String[fetchCategoryList.size()];
        for (int i = 0; i < fetchCategoryList.size(); i++) {
            stringCategoryList[i] = fetchCategoryList.get(i).getName();
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, stringCategoryList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerCategory.setAdapter(arrayAdapter);
        binding.spinnerCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (categorySelectedPos != position) {
                    FetchCategories fetchCategories = fetchCategoryList.get(position);
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
                    FetchCategories fetchCategories = fetchCategoryList.get(categorySelectedPos);
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
                if (brandSelectedPos != position) {
                    serviceCenter.setBrandId(brandList.get(position).getId());
                    serviceCenter.setBrandName(brandList.get(position).getName());
                }
                //For avoiding double tapping issue
                if (binding.spinnerBrand.getOnItemClickListener() != null) {
                    binding.spinnerBrand.onItemClick(parent, view, position, id);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        registrationServicePresenter.disposeAll();
    }
}
