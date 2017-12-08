package com.incon.service.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.HashMap;


public class PermissionUtils {

    private static PermissionUtils permissionUtils;

    private PermissionUtils() {
    }

    public static PermissionUtils getInstance() {
        if (permissionUtils == null) {
            permissionUtils = new PermissionUtils();
        }
        return permissionUtils;
    }

    private HashMap<String, Integer> resultMap;

    /**
     * Status for permission denied when "Don't ask again" was checked.
     */
    public final static int PERMISSION_DENIED_FOREVER = -1;
    public final static int PERMISSION_GRANTED = 1;
    public final static int PERMISSION_DENIED = 0;

    public void grantPermission(final Activity activity, final String requestedPermissions[],
                                final Callback callback) {
        if (resultMap == null) {
            resultMap = new HashMap<>();
        } else {
            resultMap.clear();
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (callback != null) {
                callback.onFinish(prepareFixedMap(requestedPermissions, PERMISSION_GRANTED));
            }
            return;
        }

        try {
            final ArrayList<String> permissionsList = new ArrayList<>();

            for (String permission : requestedPermissions) {
                if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                    resultMap.put(permission, PERMISSION_GRANTED);
                } else {
                    permissionsList.add(permission);
                }
            }

            if (permissionsList.size() == 0) {
                if (callback != null) {
                    callback.onFinish(prepareFixedMap(requestedPermissions, PERMISSION_GRANTED));
                }
                return;
            }

            final FragmentManager fragmentManager = activity.getFragmentManager();
             Fragment request = new Fragment() {

                private final static int PERMISSIONS_REQUEST_CODE = 99;

                @Override
                public void onCreate(Bundle saveInstanceState) {
                    super.onCreate(saveInstanceState);
                    String[] permissionsToRequest = new String[permissionsList.size()];
                    permissionsToRequest = permissionsList.toArray(permissionsToRequest);
                    requestPermissions(permissionsToRequest, PERMISSIONS_REQUEST_CODE);
                }

                @Override
                public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                       int[] grantResults) {
                    if (requestCode != PERMISSIONS_REQUEST_CODE)
                        return;

                    if (callback != null) {
                        callback.onFinish(prepareMap(activity, permissions, grantResults));
                    }

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.remove(this);
                    fragmentTransaction.commit();
                }
            };

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(0, request);
            fragmentTransaction.commit();
        } catch (Exception error) {
            if (callback != null) {
                callback.onFinish(prepareFixedMap(requestedPermissions, PERMISSION_DENIED));
            }
        }
    }

    private HashMap<String, Integer> prepareFixedMap(String permissions[], int status) {
        for (String permission : permissions) {
            resultMap.put(permission, status);
        }
        return resultMap;
    }

    private HashMap<String, Integer> prepareMap(Activity activity, String permissions[],
                                                int grantResults[]) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                resultMap.put(permissions[i], PERMISSION_GRANTED);
            } else {
                if (!activity.shouldShowRequestPermissionRationale(permissions[i])) {
                    // Permission denied with never ask again
                    resultMap.put(permissions[i], PERMISSION_DENIED_FOREVER);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    resultMap.put(permissions[i], PERMISSION_DENIED);
                }
            }
        }
        return resultMap;
    }

    public interface Callback {
        /**
         * @param permissionsStatusMap Returns permission name in Key and status in value
         *                             <p>
         *                             integer value - {@link PermissionUtils}.PERMISSION_GRANTED,
         *                             {@link PermissionUtils}.PERMISSION_DENIED and
         *                             {@link PermissionUtils}.PERMISSION_DENIED_FOREVER
         */
        void onFinish(HashMap<String, Integer> permissionsStatusMap);
    }

    public static void launchAppSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
