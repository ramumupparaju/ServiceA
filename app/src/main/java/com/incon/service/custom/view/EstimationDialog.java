package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.incon.service.R;
import com.incon.service.callbacks.EstimationDialogCallback;
import com.incon.service.databinding.DialogEstimationBinding;

/**
 * Created by MY HOME on 11-Jan-18.
 */

public class EstimationDialog extends Dialog implements View.OnClickListener {
    private final Context context;
    private EditText editTextPrice;
    private DialogEstimationBinding binding;
    private EstimationDialogCallback estimationDialogCallback;



    public EstimationDialog(AlertDialogBuilder builder) {

        super(builder.context);
        this.context = builder.context;
        this.estimationDialogCallback = builder.callback;
    }

    public void showDialog() {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.dialog_estimation, null, false);
        View contentView = binding.getRoot();
        editTextPrice = binding.edittextEstimationPrice;
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

        public AlertDialogBuilder(Context context, EstimationDialogCallback callback) {
            this.context = context;
            this.callback = callback;

        }



        public EstimationDialog build() {
            EstimationDialog dialog = new EstimationDialog(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }


    }


    @Override
    public void onClick(View view) {
        if (estimationDialogCallback == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.view_date:
                estimationDialogCallback.dateClicked("");
                break;
            case R.id.button_left:
                estimationDialogCallback.alertDialogCallback(EstimationDialogCallback.CANCEL);
                break;
            case R.id.button_right:
                break;
            default:
                break;
        }

    }

}
