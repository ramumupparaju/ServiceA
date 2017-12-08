package com.incon.service.ui.validateotp;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;


import com.incon.service.ConnectApplication;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;

/**
 * Created on 08 Jun 2017 8:31 PM.
 */
public class ValidateOtpPresenter extends
        BasePresenter<ValidateOtpContract.View> implements
        ValidateOtpContract.Presenter {

    private Context appContext;
    private static final String TAG = ValidateOtpPresenter.class.getName();

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void validateOTP(HashMap<String, String> verify) {
        DisposableObserver<LoginResponse> observer = new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                getView().hideProgress();
                getView().validateOTP(loginResponse);
            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
            }

            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        AppApiService.getInstance().validateOtp(verify).subscribe(observer);
        addDisposable(observer);
    }

    @Override
    public void validateWarrantyOTP(HashMap<String, String> verify) {
        DisposableObserver<ValidateWarrantyOtpResponse> observer =
                new DisposableObserver<ValidateWarrantyOtpResponse>() {
                    @Override
                    public void onNext(ValidateWarrantyOtpResponse loginResponse) {
                        getView().hideProgress();
                        getView().validateWarrantyOTP(loginResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                        getView().handleException(errorDetails);
                    }

                    @Override
                    public void onComplete() {
                        getView().hideProgress();
                    }
                };
        AppApiService.getInstance().validateWarrantyOtp(verify).subscribe(observer);
        addDisposable(observer);
    }
}
