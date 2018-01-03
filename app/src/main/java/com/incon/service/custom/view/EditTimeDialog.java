package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.incon.service.R;
import com.incon.service.callbacks.EditTimeCallback;
import com.incon.service.databinding.DialogEditTimeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 1/2/2018.
 */

public class EditTimeDialog extends Dialog implements View.OnClickListener {
    private DialogEditTimeBinding binding;
    private final EditTimeCallback editTimeCallback;
    private final Context context;

    public EditTimeDialog(AlertDialogBuilder builder) {
        super(builder.context);
        this.editTimeCallback = builder.callback;
        this.context = builder.context;
    }

    public void showDialog() {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.dialog_edit_time, null, false);
        View contentView = binding.getRoot();
        binding.includeRegisterBottomButtons.buttonLeft.setText(context.getString(R.string.action_cancel));
        binding.includeRegisterBottomButtons.buttonRight.setText(context.getString(R.string.action_submit));
        binding.includeRegisterBottomButtons.buttonLeft.setOnClickListener(this);
        binding.includeRegisterBottomButtons.buttonRight.setOnClickListener(this);
        binding.viewDate.setOnClickListener(this);
        binding.viewTime.setOnClickListener(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(true);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }

    public void setDateFromPicker(String dobInDD_mm_yyyy) {
        binding.edittextDate.setText(dobInDD_mm_yyyy);
    }

    public void setTimeFromPicker(String timeSlot) {
        binding.edittextTime.setText(timeSlot);
    }

    @Override
    public void onClick(View view) {
        if (editTimeCallback == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.view_date:
                editTimeCallback.dateClicked("");
                break;
            case R.id.view_time:
                editTimeCallback.timeClicked();
                break;
            case R.id.button_left:
                editTimeCallback.alertDialogCallback(EditTimeCallback.CANCEL);
                break;
            case R.id.button_right:
              /*  if (validateFields()) {
                }*/
                break;
            default:
                break;
        }

    }

    public static class AlertDialogBuilder {
        private final Context context;
        private final EditTimeCallback callback;

        public AlertDialogBuilder(Context context, EditTimeCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public EditTimeDialog build() {
            EditTimeDialog dialog = new EditTimeDialog(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }


    }

}
