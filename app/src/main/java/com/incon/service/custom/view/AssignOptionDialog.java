package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.incon.service.R;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.databinding.DialogAssignBinding;

/**
 * Created by MY HOME on 28-Dec-17.
 */

public class AssignOptionDialog extends Dialog implements View.OnClickListener {
    private final Context context;
    private final AssignOptionCallback assignOptionCallback;
    private String[] optionsArray;
    private DialogAssignBinding binding;
    private EditText editTextNotes;
    private final String submitButton; // required

    public AssignOptionDialog(AlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.assignOptionCallback = builder.callback;
        this.submitButton = builder.submitButton;
    }

    public void showDialog() {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.dialog_assign, null, false);
        View contentView = binding.getRoot();

        loadAssignSpinner();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }

    private void loadAssignSpinner() {
        Context context = binding.getRoot().getContext();
        optionsArray = context.getResources().getStringArray(R.array.gender_options_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,
                R.layout.view_spinner, optionsArray);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerUsers.setAdapter(arrayAdapter);
        binding.spinnerUsers.setText(optionsArray[0]);
    }


    public static class AlertDialogBuilder {
        private final Context context;
        private final AssignOptionCallback callback;
        private String title;
        private String submitButton;

        public AlertDialogBuilder(Context context, AssignOptionCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public AlertDialogBuilder title(String title) {
            this.title = title;
            return this;
        }
        public AlertDialogBuilder submitButtonText(String submitButton) {
            this.submitButton = submitButton;
            return this;
        }
        public AssignOptionDialog build() {
            AssignOptionDialog dialog = new AssignOptionDialog(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }
    }

    @Override
    public void onClick(View view) {
        if (assignOptionCallback == null) {
            return;
        }
    }
}
