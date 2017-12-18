package com.incon.service.ui.addservicecenter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchcategorie.Brand;
import com.incon.service.apimodel.components.fetchcategorie.Division;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.custom.view.CustomAutoCompleteView;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.databinding.FragmentAddservicecenterBinding;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.utils.DateUtils;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public class AddServiceCenterFragment extends BaseFragment implements
        AddServiceCenterContract.View {
    private View rootView;
    private AddServiceCenterPresenter addServiceCenterPresenter;
    private AddServiceCenter addServiceCenter;
    private List<FetchCategories> fetchCategorieList;
    private int categorySelectedPos = -1;
    private int divisionSelectedPos = -1;
    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;
    FragmentAddservicecenterBinding binding;


    @Override
    protected void initializePresenter() {

        addServiceCenterPresenter = new AddServiceCenterPresenter();
        addServiceCenterPresenter.setView(this);
        setBasePresenter(addServiceCenterPresenter);
    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_addservicecenter, container, false);
             addServiceCenter = new AddServiceCenter();
            binding.setAddServiceCenter(addServiceCenter);
            binding.setAddServiceCenterFragment(this);
            rootView = binding.getRoot();
             initViews();
             addServiceCenterPresenter.getCategories(SharedPrefsUtils.loginProvider().
                    getIntegerPreference(LoginPrefs.STORE_ID, DEFAULT_VALUE));
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        shakeAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        loadValidationErrors();
        setFocusForViews();

    }


    public void onDateClick() {
        showDatePicker();
    }

    private void showDatePicker() {
        AppUtils.hideSoftKeyboard(getActivity(), getView());
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());

        String createdDate = addServiceCenter.getCreatedDate();
        if (!TextUtils.isEmpty(createdDate)) {
            cal.setTimeInMillis(DateUtils.convertStringFormatToMillis(
                    createdDate, DateFormatterConstants.MM_DD_YYYY));
        }

        int customStyle = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? R.style.DatePickerDialogTheme : android.R.style.Theme_DeviceDefault_Light_Dialog;
        DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
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
                    addServiceCenter.setCreatedDate(dobInMMDDYYYY);

                    Pair<String, Integer> validate = binding.getAddServiceCenter().
                            validateAddServiceCenter((String) binding.edittextCreatedDate.getTag());
                    updateUiAfterValidation(validate.first, validate.second);
                }
            };

    public void onAddressClick() {
        Intent addressIntent = new Intent(getActivity(), RegistrationMapActivity.class);
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
                Pair<String, Integer> validation = binding.getAddServiceCenter().
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
        binding.spinnerCategory.setError(null);
        binding.spinnerDivision.setError(null);
        binding.spinnerBrand.setError(null);
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
        errorMap.put(AddServiceCenterValidation.NAME, getString(R.string.error_name_req));
        errorMap.put(AddServiceCenterValidation.MOBILE_NUMBER, getString(R.string.error_phone_req));
        errorMap.put(AddServiceCenterValidation.EMAIL, getString(R.string.error_email_req));
        errorMap.put(AddServiceCenterValidation.ADDRESS, getString(R.string.error_address_req));
        errorMap.put(AddServiceCenterValidation.CREATED_Date, getString(R.string.error_created_date));
        errorMap.put(AddServiceCenterValidation.CATEGORY, getString(R.string.error_product_category));
        errorMap.put(AddServiceCenterValidation.DIVISION, getString(R.string.error_product_division));
        errorMap.put(AddServiceCenterValidation.BRAND, getString(R.string.error_product_brand));
        errorMap.put(AddServiceCenterValidation.GSTN, getString(R.string.error_gstn_req));

    }

    public void onSubmitClick() {
        if (validateFields()) {
            addServiceCenterPresenter.addingserviceCenter(SharedPrefsUtils.loginProvider().
                    getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE), addServiceCenter);
        }
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
                    addServiceCenter.setCategoryId(fetchCategories.getId());
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
                    addServiceCenter.setDivisionId(divisions1.getId());
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
                addServiceCenter.setBrandId(brandList.get(position).getId());
                //For avoiding double tapping issue
                if (binding.spinnerBrand.getOnItemClickListener() != null) {
                    binding.spinnerBrand.onItemClick(parent, view, position, id);
                }
            }
        });
    }
}
