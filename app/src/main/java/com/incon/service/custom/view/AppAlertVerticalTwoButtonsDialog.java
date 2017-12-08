package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.incon.service.R;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.utils.DeviceUtils;


public class AppAlertVerticalTwoButtonsDialog extends Dialog implements View.OnClickListener {
    private final Context context;
    //All final attributes
    private final String title; // optional
    private final String button1text; // required
    private final String button2text; // required
    private final AlertDialogCallback mAlertDialogCallback; // required
    private int buttonOrientation;
    private Button mFirstButtonTextView;
    private Button mSecondButtonTextView;

    /**
     * @param builder
     */
    private AppAlertVerticalTwoButtonsDialog(AlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.title = builder.title;
        this.button1text = builder.button1Text;
        this.button2text = builder.button2Text;
        this.mAlertDialogCallback = builder.callback;
        this.buttonOrientation = builder.buttonOrientation;
    }

    public void showDialog() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.dialog_app_vertical_two_buttons, null);

        ((LinearLayout) contentView.findViewById(R.id.button_layout)).setOrientation(
                buttonOrientation);

        TextView titleTv = ((TextView) contentView.findViewById(R.id.dialog_title_textView));
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        } else {
            titleTv.setVisibility(View.GONE);
        }

        mFirstButtonTextView = (Button) contentView.findViewById(R.id.dialog_first_button);
        if (!TextUtils.isEmpty(button1text)) {
            mFirstButtonTextView.setText(button1text);
        }
        mFirstButtonTextView.setOnClickListener(this);
        mSecondButtonTextView = (Button) contentView.findViewById(R.id.dialog_second_button);
        if (!TextUtils.isEmpty(button2text)) {
            mSecondButtonTextView.setText(button2text);
        }
        mSecondButtonTextView.setOnClickListener(this);

        if (buttonOrientation == LinearLayout.HORIZONTAL) {
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) mFirstButtonTextView.getLayoutParams();
            layoutParams.setMargins(0, 0, (int) DeviceUtils.convertPxToDp(5), 0);
             layoutParams =
                    (LinearLayout.LayoutParams) mSecondButtonTextView.getLayoutParams();
            layoutParams.setMargins((int) DeviceUtils.convertPxToDp(5), 0, 0, 0);
        }

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
            case R.id.dialog_first_button:
                mAlertDialogCallback.alertDialogCallback(AlertDialogCallback.OK);
                break;
            case R.id.dialog_second_button:
                mAlertDialogCallback.alertDialogCallback(AlertDialogCallback.CANCEL);
                break;
            default:
                break;
        }
    }

    public static class AlertDialogBuilder {

        private final Context context;
        private final AlertDialogCallback callback;
        private String title;
        private String button1Text;
        private String button2Text;
        private int buttonOrientation = LinearLayout.VERTICAL;


        public AlertDialogBuilder(Context context, AlertDialogCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public AlertDialogBuilder title(String title) {
            this.title = title;
            return this;
        }

        public AlertDialogBuilder button1Text(String button1Text) {
            this.button1Text = button1Text;
            return this;
        }

        public AlertDialogBuilder button2Text(String button2Text) {
            this.button2Text = button2Text;
            return this;
        }
        public AlertDialogBuilder setButtonOrientation(int buttonOrientation) {
            this.buttonOrientation = buttonOrientation;
            return this;
        }


        //Return the finally constructed User object
        public AppAlertVerticalTwoButtonsDialog build() {
            AppAlertVerticalTwoButtonsDialog dialog = new AppAlertVerticalTwoButtonsDialog(
                    this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }
    }

    public void setButtonBlueUnselectBackground() {
        mFirstButtonTextView.setBackground(ContextCompat.getDrawable(context,
                R.drawable.btn_logout_roundedcorner));
        mFirstButtonTextView.setTextColor(ContextCompat.getColorStateList(context,
                R.color.text_river_bed_white_selected));
        mSecondButtonTextView.setBackground(ContextCompat.getDrawable(context,
                R.drawable.btn_logout_roundedcorner));
        mSecondButtonTextView.setTextColor(ContextCompat.getColorStateList(context,
                R.color.text_river_bed_white_selected));
    }
}
