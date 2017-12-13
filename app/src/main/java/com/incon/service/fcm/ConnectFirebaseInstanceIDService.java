package com.incon.service.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.incon.service.ui.notifications.PushPresenter;

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

        PushPresenter pushPresenter = new PushPresenter();
        pushPresenter.pushRegisterApi();
    }
}
