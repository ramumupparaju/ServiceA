package com.incon.service;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AppUtils {

    public static void shortToast(Context context, String toastMessage) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Context context, String toastMessage) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
    }

    public static void callPhoneNumber(Context context, String phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            Uri number = Uri.parse("tel:" + phoneNumber);
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            context.startActivity(callIntent);
        }
    }

    public static void showSnackBar(View view, String message) {

        final Snackbar snackbar = Snackbar.make(view, message, 6000);
        snackbar.setAction("dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public static void loadImageFromApi(ImageView imageView, String url) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_placeholder);
        requestOptions.error(R.drawable.ic_placeholder);
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(BuildConfig.SERVICE_ENDPOINT + url)
                .into(imageView);
    }

    public static void showSoftKeyboard(Context ctx, View v) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void hideSoftKeyboard(Context ctx, View v) {
        InputMethodManager inputManager = (InputMethodManager) ctx.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static String pojoToJson(Object response) {

        Gson gson = new GsonBuilder().create();
        return gson.toJson(response);
    }

    public static <T> T jsonToPojo(Class<T> aClass, String json) {

        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, aClass);
    }


}
