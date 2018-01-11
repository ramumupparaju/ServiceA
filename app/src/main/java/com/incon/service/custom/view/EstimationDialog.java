package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;

import com.incon.service.AppConstants;
import com.incon.service.R;
import com.incon.service.apimodel.components.updatestatus.Status;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.callbacks.EstimationDialogCallback;
import com.incon.service.databinding.DialogEstimationBinding;
import com.incon.service.dto.updatestatus.UpDateStatus;

/**
 * Created by MY HOME on 11-Jan-18.
 */

public class EstimationDialog extends Dialog implements View.OnClickListener {
    private final Context context;
    private DialogEstimationBinding binding;
    private EstimationDialogCallback estimationDialogCallback;
    private final UpDateStatus upDateStatus;


    public EstimationDialog(AlertDialogBuilder builder) {

        super(builder.context);
        this.context = builder.context;
        this.estimationDialogCallback = builder.callback;
        upDateStatus = new UpDateStatus();
    }

    public void showDialog() {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.dialog_estimation, null, false);
        View contentView = binding.getRoot();
        binding.includeRegisterBottomButtons.buttonLeft.setText(context.getString(R.string.action_cancel));
        binding.includeRegisterBottomButtons.buttonRight.setText(context.getString(R.string.action_submit));
        binding.includeRegisterBottomButtons.buttonLeft.setOnClickListener(this);
        binding.includeRegisterBottomButtons.buttonRight.setOnClickListener(this);
        binding.viewDate.setOnClickListener(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(true);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }

    public void setDateFromPicker(String dobInDD_mm_yyyy) {
        binding.edittextEstimationTime.setText(dobInDD_mm_yyyy);
    }

    public static class AlertDialogBuilder {
        private final Context context;
        private final EstimationDialogCallback callback;
        private String title;

        public AlertDialogBuilder(Context context, EstimationDialogCallback callback) {
            this.context = context;
            this.callback = callback;

        }


        public EstimationDialog build() {
            EstimationDialog dialog = new EstimationDialog(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }


        public AlertDialogBuilder title(String title) {
            this.title = title;
            return this;
        }
    }


    @Override
    public void onClick(View view) {
        if (estimationDialogCallback == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.view_date:
                estimationDialogCallback.dateClicked(binding.edittextEstimationTime.getText().toString());
                break;
            case R.id.button_left:
                estimationDialogCallback.alertDialogCallback(EstimationDialogCallback.CANCEL);
                break;
            case R.id.button_right:
                if (validateFields()) {
                    estimationDialogCallback.doUpDateStatusApi(upDateStatus);
                    estimationDialogCallback.alertDialogCallback(AssignOptionCallback.OK);
                }
                break;
            default:
                break;
        }

    }

    private boolean validateFields() { //TODO have to add validations

        int selectedApproval = binding.radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedApproval);

        String radioText = radioButton.getText().toString();

        int statusId;
        if (radioText.equalsIgnoreCase(getContext().getString(R.string.action_manual_approval))) {
            statusId = AppConstants.StatusConstants.REPAIR;
        } else {
            statusId = AppConstants.StatusConstants.WAIT_APPROVE;
        }
        upDateStatus.setStatus(new Status(statusId));
        upDateStatus.setComments(binding.edittextEstimationTime.getText().toString());
        return true;
    }

}
