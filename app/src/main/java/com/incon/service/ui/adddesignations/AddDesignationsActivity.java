package com.incon.service.ui.adddesignations;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.incon.service.AppConstants;
import com.incon.service.R;
import com.incon.service.apimodel.components.servicecenterresponse.ServiceCenterResponse;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.databinding.ActivityAddDesignationsBinding;
import com.incon.service.dto.adddesignation.AddDesignation;
import com.incon.service.ui.BaseActivity;
import com.incon.service.utils.SharedPrefsUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PC on 12/19/2017.
 */

public class AddDesignationsActivity extends BaseActivity implements
        AddDesignationsContract.View {
    private View rootView;
    private AddDesignationsPresenter addDesignationsPresenter;
    private ActivityAddDesignationsBinding binding;
    private AddDesignation addDesignation;
    public List<ServiceCenterResponse> serviceCenterResponseList;
    private int serviceCenterSelectedPos = -1;
    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;
    private int userId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_designations;
    }

    @Override
    protected void initializePresenter() {
        addDesignationsPresenter = new AddDesignationsPresenter();
        addDesignationsPresenter.setView(this);
        setBasePresenter(addDesignationsPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        addDesignation = new AddDesignation();
        binding.setAddDesignation(addDesignation);
        binding.setAddDesignationsActivity(this);
        rootView = binding.getRoot();
        initViews();
        addDesignationsPresenter.serviceCentersList(SharedPrefsUtils.loginProvider().
                getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE));
        initializeToolbar();
    }

    private void initializeToolbar() {
        binding.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initViews() {
        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        loadValidationErrors();
        setFocusForViews();
    }

    private void loadServiceCenterSpinnerData() {
        //TODO have to remove hardcoding and comment lines

      /*  serviceCenterResponseList = new ArrayList<>();
        ServiceCenterResponse serviceCenterResponse = new ServiceCenterResponse();
        serviceCenterResponse.setId(serviceCenterResponse.getId());
        serviceCenterResponse.setName(serviceCenterResponse.getName());*/
        //   serviceCenterResponseList.add(serviceCenterResponse);
    /*
     serviceCenterResponse.setId(Integer.valueOf("1"));
      serviceCenterResponse.setName("moonzdream");
      serviceCenterResponseList.add(serviceCenterResponse);
    serviceCenterResponse = new ServiceCenterResponse();
    serviceCenterResponse.setId(Integer.valueOf("2"));
   serviceCenterResponse.setName("incon");*/

     //   serviceCenterResponseList.add(serviceCenterResponse);

           /*   List<String> serviceCenterNamesList = new ArrayList<>();
        for (ServiceCenterResponse centerResponse : serviceCenterResponseList) {
            serviceCenterNamesList.add(centerResponse.getName());
        }*/

        String[] serviceCenterNamesList = new String[serviceCenterResponseList.size()];
        for (int i = 0; i < serviceCenterResponseList.size(); i++) {
            serviceCenterNamesList[i] = serviceCenterResponseList.get(i).getName();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.view_spinner, serviceCenterNamesList);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerServiceCenter.setAdapter(arrayAdapter);
        binding.spinnerServiceCenter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (serviceCenterSelectedPos != position) {
                    ServiceCenterResponse serviceCenterResponse = serviceCenterResponseList.get(position);
                    addDesignation.setServiceCenterId(serviceCenterResponse.getId());
                    addDesignation.setName(serviceCenterResponse.getName());
                    serviceCenterSelectedPos = position;
                }
                //For avoiding double tapping issue
                if (binding.spinnerServiceCenter.getOnItemClickListener() != null) {
                    binding.spinnerServiceCenter.onItemClick(parent, view, position, id);
                }
            }
        });

    }

    private void loadValidationErrors() {
        errorMap = new HashMap<>();
        errorMap.put(AddDesignationsValidation.NAME_REQ,
                getString(R.string.error_name_req));
        errorMap.put(AddDesignationsValidation.DESCRIPTION,
                getString(R.string.error_desc_req));
        errorMap.put(AddDesignationsValidation.SERVICE_CENTER_NAME,
                getString(R.string.error_service_center_name_req));

    }

    private void setFocusForViews() {
        TextView.OnEditorActionListener onEditorActionListener =
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            switch (textView.getId()) {
                                case R.id.edittext_description:
                                    break;

                                default:
                            }
                        }
                        return true;
                    }
                };
        binding.edittextDescription.setOnEditorActionListener(onEditorActionListener);
        binding.edittextName.setOnFocusChangeListener(onFocusChangeListener);
    }


    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            Object fieldId = view.getTag();
            if (fieldId != null) {
                Pair<String, Integer> validation = addDesignation.
                        validateAddDesignations((String) fieldId);
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
        binding.inputLayoutDescription.setError(null);
        binding.spinnerServiceCenter.setError(null);

        Pair<String, Integer> validation = binding.getAddDesignation().validateAddDesignations(null);
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

    public void onSubmitClick() {
        if (validateFields()) {
            int isAdmin = binding.checkboxAdmin.isChecked() ? BooleanConstants.IS_TRUE : BooleanConstants
                    .IS_FALSE;
            addDesignation.setIsAdmin(isAdmin);
            addDesignation.setServiceCenterId(serviceCenterResponseList.get
                    (serviceCenterSelectedPos).getId());
            addDesignationsPresenter.addDesignations(SharedPrefsUtils.loginProvider().
                    getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE), addDesignation);
        }

    }

    @Override
    public void loadServiceCentersList(List<ServiceCenterResponse> serviceCenterResponseList) {
        this.serviceCenterResponseList = serviceCenterResponseList;
        loadServiceCenterSpinnerData();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        addDesignationsPresenter.disposeAll();
    }


}
