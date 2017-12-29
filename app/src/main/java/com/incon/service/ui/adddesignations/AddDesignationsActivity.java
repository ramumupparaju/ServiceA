package com.incon.service.ui.adddesignations;

import android.content.Intent;
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

import com.incon.service.R;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.databinding.ActivityAddDesignationsBinding;
import com.incon.service.dto.adddesignation.AddDesignation;
import com.incon.service.ui.BaseActivity;
import com.incon.service.utils.SharedPrefsUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PC on 12/19/2017.
 */

public class AddDesignationsActivity extends BaseActivity implements
        AddDesignationsContract.View {

    private AddDesignationsPresenter addDesignationsPresenter;
    private ActivityAddDesignationsBinding binding;

    private AddDesignation addDesignation;
    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;
    private int serviceCenterId;

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

        Intent bundle = getIntent();
        if (bundle != null)
            addDesignation = bundle.getParcelableExtra(IntentConstants.DESIGNATION_DATA);
        if (addDesignation != null) {
        } else {

        }

        addDesignation = new AddDesignation();
        binding.setAddDesignation(addDesignation);
        binding.setAddDesignationsActivity(this);

        //loading data from intent
        Intent intent = getIntent();
        serviceCenterId = intent.getIntExtra(IntentConstants.SERVICE_CENTER_DATA, DEFAULT_VALUE);
        addDesignation.setServiceCenterId(serviceCenterId);

        initViews();
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
        initializeToolbar();

        shakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake);
        loadValidationErrors();
        setFocusForViews();
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
            addDesignationsPresenter.addDesignations(SharedPrefsUtils.loginProvider().
                    getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE), addDesignation);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addDesignationsPresenter.disposeAll();
    }
}
