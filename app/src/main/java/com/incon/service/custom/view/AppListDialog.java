package com.incon.service.custom.view;

import android.content.Context;
import android.content.DialogInterface;

import com.incon.service.callbacks.IClickCallback;

import java.io.Serializable;


public class AppListDialog implements Serializable {

    private static final String TAG = AppListDialog.class.getSimpleName();
    public IClickCallback clickCallback;

    public void setClickCallback(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public void initDialogLayout(Context context, String[] array) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context,
                android.R.style.Theme_Material_Dialog_Alert);
        builder.setItems(array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int position) {
                dialogInterface.dismiss();
                clickCallback.onClickPosition(position);
            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }
}
