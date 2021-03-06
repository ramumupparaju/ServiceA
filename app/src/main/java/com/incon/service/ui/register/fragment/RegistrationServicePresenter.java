package com.incon.service.ui.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.dto.registration.Registration;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;
import okhttp3.MultipartBody;


public class RegistrationServicePresenter extends
        BasePresenter<RegistrationServiceContract.View> implements
        RegistrationServiceContract.Presenter {

    private static final String TAG = RegistrationServicePresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    /**
     * Uploading user and store details to server
     *
     * @param registrationBody
     */
    @Override
    public void register(Registration registrationBody) {
        getView().showProgress(appContext.getString(R.string.progress_registering));
        DisposableObserver<LoginResponse> observer = new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                // TODO have to save logon details and navigate to home
              // getView().uploadServiceCenterLogo(loginResponse.getStore().getId());
                getView().navigateToLoginScreen();
            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
            }

            @Override
            public void onComplete() {
            }
        };
        AppApiService.getInstance().register(registrationBody).subscribe(observer);
        addDisposable(observer);
    }


    @Override
    public void uploadServiceCenterLogo(int serviceCenterId, MultipartBody.Part serviceCenterLogo) {
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object loginResponse) {
                // save login data to shared preferences
                getView().hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
            }

            @Override
            public void onComplete() {
            }
        };
        AppApiService.getInstance().uploadServiceCenterLogo(serviceCenterId, serviceCenterLogo).
                subscribe(observer);
        addDisposable(observer);

    }

}
