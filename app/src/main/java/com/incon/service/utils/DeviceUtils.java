package com.incon.service.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;


import com.incon.service.ConnectApplication;

import java.util.UUID;

public class DeviceUtils {

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public static float convertDpToPx(float valueInDp) {
        DisplayMetrics metrics = ConnectApplication.getAppContext().
                getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    public static int convertSpToPixels(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

    public static float convertPxToDp(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                ConnectApplication.getAppContext().getResources().getDisplayMetrics());
    }
    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    public static String getUniqueID() {
        if (uniqueID == null) {
            SharedPrefsUtils appProvider = SharedPrefsUtils.appProvider();
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                appProvider.setStringPreference(PREF_UNIQUE_ID, uniqueID);
            }
        }
        return uniqueID;
    }
}
