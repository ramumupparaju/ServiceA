package com.incon.service.ui.notifications;

import android.os.Build;
import android.os.Bundle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.incon.service.AppConstants;
import com.incon.service.api.AppApiService;
import com.incon.service.dto.notifications.PushRegistrarBody;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.DeviceUtils;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.TimeZone;

import io.reactivex.observers.DisposableObserver;

/**
 * Created on 31 May 2017 11:19 AM.
 */
public class PushPresenter extends BasePresenter<PushContract.View> implements
        PushContract.Presenter {

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
    }


    @Override
    public void pushRegisterApi() {
        int userId = SharedPrefsUtils.loginProvider()
                .getIntegerPreference(AppConstants.LoginPrefs.USER_ID, AppConstants.DEFAULT_VALUE);
        if (userId == AppConstants.DEFAULT_VALUE) {
            return;
        }

        PushRegistrarBody pushRegistrarBody = new PushRegistrarBody();

        pushRegistrarBody.setuId(DeviceUtils.getUniqueID());
        pushRegistrarBody.setPushKey(FirebaseInstanceId.getInstance().getToken());
        pushRegistrarBody.setOsVersion(String.valueOf(Build.VERSION.SDK_INT));
        pushRegistrarBody.setManufacturer(Build.MANUFACTURER);
        pushRegistrarBody.setLocale(TimeZone.getDefault().getID());
        pushRegistrarBody.setModel(Build.MODEL);
        pushRegistrarBody.setDeviceType(AppConstants.PushSubTypeConstants.PUSH_DEVICE_TYPE);

        DisposableObserver<Object> observer =
                new DisposableObserver<Object>() {
                    @Override
                    public void onNext(Object object) {
                        SharedPrefsUtils.cacheProvider().setBooleanPreference(AppConstants
                                .LoginPrefs.PUSH_TOKEN_STATUS, true);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                };

        AppApiService.getInstance().pushTokenApi(userId,pushRegistrarBody).subscribe(observer);
        addDisposable(observer);
    }

}
