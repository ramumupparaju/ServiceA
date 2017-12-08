package com.incon.service.custom.view;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.incon.service.R;
import com.incon.service.callbacks.TextAddressDialogCallback;
import com.incon.service.databinding.ViewUserProductAddressBinding;
import com.incon.service.dto.addfavorites.AddUserAddress;


/**
 * Created by PC on 11/8/2017.
 */

public class AppUserAddressDialog extends Dialog implements View.OnClickListener {
    private final Context context;
    private AddUserAddress addUserAddress;
    private ViewUserProductAddressBinding binding;
    private final TextAddressDialogCallback mAlertDialogCallback; // required
    private AppUserAddressDialog(AlertDialogBuilder builder) {
        super(builder.context);
        this.context = builder.context;
        this.addUserAddress = builder.addUserAddress;
        this.mAlertDialogCallback = builder.callback;
    }

    public void showDialog() {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.view_user_product_address, null, false);
        binding.setAppAddressDialog(AppUserAddressDialog.this);
        binding.setAddUserAddress(addUserAddress);
        View contentView = binding.getRoot();
        binding.includeRegisterBottomButtons.buttonLeft.setText(
                context.getString(R.string.action_cancel));
        binding.includeRegisterBottomButtons.buttonRight.setText(
                context.getString(R.string.action_submit));
        binding.includeRegisterBottomButtons.buttonLeft.setOnClickListener(this);
        binding.includeRegisterBottomButtons.buttonRight.setOnClickListener(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(contentView);
        setCancelable(false);
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shadow);
        show();
    }

    public void onAddressClick() {
        mAlertDialogCallback.openAddressActivity();
    }

    public AddUserAddress getAddUserAddress() {
        return addUserAddress;
    }

    public void setAddUserAddress(AddUserAddress addUserAddress) {
        this.addUserAddress = addUserAddress;
        binding.setAddUserAddress(addUserAddress);
    }

    @Override
    public void onClick(View view) {
        if (mAlertDialogCallback == null) {
            return;
        }
        switch (view.getId()) {
            case R.id.button_left:
                mAlertDialogCallback.alertDialogCallback(TextAddressDialogCallback.CANCEL);
                break;
            case R.id.button_right:
                mAlertDialogCallback.alertDialogCallback(TextAddressDialogCallback.OK);
                break;
            default:
                break;
        }
    }

    public static class AlertDialogBuilder {
        private final Context context;
        private final TextAddressDialogCallback callback;
        private AddUserAddress addUserAddress;

        public AlertDialogBuilder(Context context, TextAddressDialogCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        public AlertDialogBuilder addUserAddress(AddUserAddress addUserAddress) {
            this.addUserAddress = addUserAddress;
            return this;
        }

        //Return the finally constructed User object
        public AppUserAddressDialog build() {
            AppUserAddressDialog dialog = new AppUserAddressDialog(this);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
            return dialog;
        }

    }
}
