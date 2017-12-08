package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;


import com.incon.service.R;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.databinding.ViewCheckboxSpinnerBinding;
import com.incon.service.dto.dialog.CheckedModelSpinner;

import java.util.List;

public class AppCheckBoxListDialog extends Dialog implements View.OnClickListener {
    private final Context context;
    private final TextAlertDialogCallback mAlertDialogCallback; // required
    //All final attributes
    private final String title; // required
    private AppCheckBoxListAdapter listAdapter;
    private List<CheckedModelSpinner> spinnerList;


    /**
     * @param builder
     */
    private AppCheckBoxListDialog(AlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.title = builder.title;
        this.spinnerList = builder.spinnerList;
        this.mAlertDialogCallback = builder.callback;
    }

    public void showDialog() {
        ViewCheckboxSpinnerBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.view_checkbox_spinner, null, false);
        View contentView = binding.getRoot();

        binding.textCheckboxTitle.setText(title);
        listAdapter = new AppCheckBoxListAdapter(spinnerList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
       /* DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), linearLayoutManager.getOrientation());
        binding.listRecyclerview.addItemDecoration(dividerItemDecoration);*/
        binding.listRecyclerview.setAdapter(listAdapter);
        binding.listRecyclerview.setLayoutManager(linearLayoutManager);

        binding.includeRegisterBottomButtons.buttonLeft.setText(
                context.getString(R.string.action_cancel));
        binding.includeRegisterBottomButtons.buttonRight.setText(
                context.getString(R.string.action_ok));
        binding.includeRegisterBottomButtons.buttonLeft.setOnClickListener(this);
        binding.includeRegisterBottomButtons.buttonRight.setOnClickListener(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(true);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }

    @Override
    public void onClick(View view) {
        if (mAlertDialogCallback == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.button_left:
                mAlertDialogCallback.alertDialogCallback(AlertDialogCallback.CANCEL);
                break;
            case R.id.button_right:
                mAlertDialogCallback.enteredText(listAdapter.getSelectedItems());
                mAlertDialogCallback.alertDialogCallback(AlertDialogCallback.OK);
                break;
            default:
                break;
        }
    }

    public void setRadioType(boolean isRadio) {
        listAdapter.setRadio(isRadio);
    }

    public static class AlertDialogBuilder {
        private final Context context;
        private final TextAlertDialogCallback callback;
        private String title;
        private List<CheckedModelSpinner> spinnerList;


        public AlertDialogBuilder(Context context, TextAlertDialogCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public AlertDialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AlertDialogBuilder spinnerItems(List<CheckedModelSpinner> spinnerList) {
            this.spinnerList = spinnerList;
            return this;
        }

        //Return the finally constructed User object
        public AppCheckBoxListDialog build() {
            AppCheckBoxListDialog dialog = new AppCheckBoxListDialog(
                    this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }
    }
}
