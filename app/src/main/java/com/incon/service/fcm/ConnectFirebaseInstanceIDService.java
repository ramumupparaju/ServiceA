package com.incon.service.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by sunil on 30/12/16.
 */
public class ConnectFirebaseInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FCm", "gcm token: " + refreshedToken);

//        SharedPreferenceUtils.writeString(AppConstants.PreferenceConstants
// .PREF_FCM_TOKEN, refreshedToken);
    }
}
