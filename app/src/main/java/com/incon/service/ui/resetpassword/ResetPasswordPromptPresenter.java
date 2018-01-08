package com.incon.service.ui.resetpassword;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.service.login.LoginDataManagerImpl;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.validateotp.ValidateOtpContract;
import com.incon.service.ui.validateotp.ValidateOtpPresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;


public class ResetPasswordPromptPresenter extends
        BasePresenter<ResetPasswordPromptContract.View> implements
        ResetPasswordPromptContract.Presenter {


    private Context appContext;
    private static final String TAG = ResetPasswordPromptPresenter.class.getName();
    private LoginDataManagerImpl loginDataManagerImpl;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
        loginDataManagerImpl = new LoginDataManagerImpl();
    }

    @Override
    public void validateOTP(HashMap<String, String> verify) {
        getView().showProgress(appContext.getString(R.string.validating_code));
        ValidateOtpPresenter otpPresenter = new ValidateOtpPresenter();
        otpPresenter.initialize(null);
        otpPresenter.setView(otpView);
        otpPresenter.validateOTP(verify);

    }

    @Override
    public void doResendOtpApi(String phoneNumber) {

        getView().showProgress(appContext.getString(R.string.progress_resend));
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object loginResponse) {
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
        AppApiService.getInstance().registerRequestPasswordOtp(phoneNumber).subscribe(observer);
        addDisposable(observer);
    }

    //TODO have to change based on requirements
    ValidateOtpContract.View otpView = new ValidateOtpContract.View() {
        @Override
        public void validateOTP(LoginResponse loginResponse) {
// save login data to shared preferences
            loginDataManagerImpl.saveLoginDataToPrefs(loginResponse);
            getView().hideProgress();
            getView().navigateToHomeScreen();
        }

        @Override
        public void validateWarrantyOTP(ValidateWarrantyOtpResponse warrantyOtpResponse) {
            //DO nothing
        }

        @Override
        public void showProgress(String message) {
            getView().showProgress(message);
        }

        @Override
        public void hideProgress() {
            getView().hideProgress();
        }

        @Override
        public void showErrorMessage(String errorMessage) {
            getView().showErrorMessage(errorMessage);
        }

        @Override
        public void handleException(Pair<Integer, String> error) {
            getView().handleException(error);
        }
    };
}
