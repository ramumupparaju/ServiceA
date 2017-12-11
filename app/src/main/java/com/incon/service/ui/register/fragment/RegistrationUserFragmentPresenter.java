package com.incon.service.ui.register.fragment;

import android.content.Context;
import android.os.Bundle;

import com.incon.service.ConnectApplication;
import com.incon.service.ui.BasePresenter;


public class RegistrationUserFragmentPresenter extends
        BasePresenter<RegistrationUserFragmentContract.View> implements
        RegistrationUserFragmentContract.Presenter {


    private Context appContext;
    private static final String TAG = RegistrationUserFragmentPresenter.class.getName();

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    /*public Observable<SendOtpResponse> getSendOtpObserver(HashMap<String, String> email) {
        return TueoApiService.getInstance().sendOtp(email);
    }

    public Observable<ValidateOtpResponse> getVerifyOtpObserver(HashMap<String, String> verify) {
        return TueoApiService.getInstance().verifyOtp(verify);
    }

    public Observable<ValidateEmailResponse> getValidateEmailObserver(String email) {
        return TueoApiService.getInstance().validateEmail(email);
    }

    @Override
    public void sendOtp(HashMap<String, String> email) {
        getView().showProgress(appContext.getString(R.string.progress_sendotp));
        Observable<SendOtpResponse> sendOtpObserver = getSendOtpObserver(email);
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().hideProgress();
                getView().showVerifyEmailPopup();
            }
            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                getView().handleException(ErrorMsgUtil.getErrorDetails(e));
            }
            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        sendOtpObserver.subscribe(observer);
        addDisposable(observer);
    }

    @Override
    public void verifyOtp(HashMap<String, String> verifyOtpMap) {
        getView().showProgress(appContext.getString(R.string.progress_verifycode));
        Observable<ValidateOtpResponse> veriftOtpObserver = getVerifyOtpObserver(verifyOtpMap);
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {

                ValidateOtpResponse validateOtpResponse = (ValidateOtpResponse) o;
                Logger.v(TAG, "==> responseCallback : StatusCode = "
                        + validateOtpResponse.getStatusCode());

                int statusCode = validateOtpResponse.getStatusCode();
                switch (statusCode) {
                    case 200:
                        getView().navigateToNext();
                        break;
                    default:
                        String errorMsg = validateOtpResponse.getMessage();
                        getView().showErrorMessage(errorMsg);
                        break;
                }
            }
            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                getView().handleException(ErrorMsgUtil.getErrorDetails(e));
            }
            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        veriftOtpObserver.subscribe(observer);
        addDisposable(observer);
    }*/

    /*@Override
    public void validateEmailInUse(String email) {
        getView().showProgress(appContext.getString(R.string.progress_verifyemail));
        Observable<ValidateEmailResponse> veriftOtpObserver = getValidateEmailObserver(email);
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().hideProgress();
                ValidateEmailResponse validateEmailResponse = (ValidateEmailResponse) o;
                getView().onValidateEmailInUseCheck(validateEmailResponse);
            }
            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                getView().handleException(ErrorMsgUtil.getErrorDetails(e));
            }
            @Override
            public void onComplete() {
            }
        };
        veriftOtpObserver.subscribe(observer);
        addDisposable(observer);
    }*/

}
