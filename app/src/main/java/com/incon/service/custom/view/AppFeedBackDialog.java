package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.incon.service.R;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.databinding.ViewFeedBackDialogBinding;


public class AppFeedBackDialog extends Dialog implements View.OnClickListener {
    private final Context context;
    //All final attributes
    private final String title; // required
    private final String leftButtonText; // required
    private final String rightButtonText; // required
    private EditText editTextNotes; // required
    private final TextAlertDialogCallback mAlertDialogCallback; // required

    /**
     * @param builder
     */
    private AppFeedBackDialog(AlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.title = builder.title;
        this.leftButtonText = builder.leftButtonText;
        this.rightButtonText = builder.rightButtonText;
        this.mAlertDialogCallback = builder.callback;
    }

    public void showDialog() {
        ViewFeedBackDialogBinding viewFeedBackDialogBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.view_feed_back_dialog, null, false);
        View contentView = viewFeedBackDialogBinding.getRoot();

        editTextNotes = viewFeedBackDialogBinding.edittextUsername;
        viewFeedBackDialogBinding.textVerifyTitle.setText(title);
        if (title.equals(getContext().getString(
                R.string.action_feedback))) {
            viewFeedBackDialogBinding.inputLayoutVerify.setHint(getContext().getString(
                    R.string.action_feedback));
        }
        viewFeedBackDialogBinding.includeRegisterBottomButtons.buttonLeft.setText(
                TextUtils.isEmpty(leftButtonText) ? context.getString(
                        R.string.action_back) : leftButtonText);
        viewFeedBackDialogBinding.includeRegisterBottomButtons.buttonRight.setText(
                TextUtils.isEmpty(leftButtonText) ? context.getString(
                        R.string.action_next) : rightButtonText);
        viewFeedBackDialogBinding.includeRegisterBottomButtons.buttonLeft.setOnClickListener(this);
        viewFeedBackDialogBinding.includeRegisterBottomButtons.buttonRight.setOnClickListener(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(false);
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
                mAlertDialogCallback.alertDialogCallback(TextAlertDialogCallback.CANCEL);
                break;
            case R.id.button_right:
                mAlertDialogCallback.enteredText(editTextNotes.getText().toString());
                mAlertDialogCallback.alertDialogCallback(TextAlertDialogCallback.OK);
                break;
            default:
                break;
        }
    }

    public static class AlertDialogBuilder {
        private final Context context;
        private final TextAlertDialogCallback callback;
        private String title;
        private String leftButtonText;
        private String rightButtonText;


        public AlertDialogBuilder(Context context, TextAlertDialogCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public AlertDialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AlertDialogBuilder leftButtonText(String leftButtonText) {
            this.leftButtonText = leftButtonText;
            return this;
        }

        public AlertDialogBuilder rightButtonText(String rightButtonText) {
            this.rightButtonText = rightButtonText;
            return this;
        }

        //Return the finally constructed User object
        public AppFeedBackDialog build() {
            AppFeedBackDialog dialog = new AppFeedBackDialog(
                    this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }
    }
}
