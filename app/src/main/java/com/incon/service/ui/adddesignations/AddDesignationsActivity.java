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
import android.widget.TextView;

import com.incon.service.R;
import com.incon.service.custom.view.CustomAutoCompleteView;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.databinding.ActivityAddDesignationsBinding;
import com.incon.service.dto.adddesignation.AddDesignation;
import com.incon.service.ui.BaseActivity;

import java.util.HashMap;

/**
 * Created by PC on 12/19/2017.
 */

public class AddDesignationsActivity  extends BaseActivity implements
        AddDesignationsContract.View{
   private AddDesignationsPresenter addDesignationsPresenter;
   private ActivityAddDesignationsBinding binding;
   private AddDesignation addDesignation;
    private View rootView;
    private HashMap<Integer, String> errorMap;
    private Animation shakeAnim;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_designations;
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        rootView = binding.getRoot();
        initViews();

    }

    private void initViews() {
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
        } else if (view instanceof CustomAutoCompleteView) {
            ((CustomTextInputLayout) view.getParent().getParent())
                    .setError(validationId == VALIDATION_SUCCESS ? null
                            : errorMap.get(validationId));
        }

        if (validationId != VALIDATION_SUCCESS) {
            view.startAnimation(shakeAnim);
        }
    }


    public void onSubmitClick() {

    }
}
