package com.incon.service.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;


import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.service.dto.login.LoginUserData;
import com.incon.service.login.LoginDataManagerImpl;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.register.fragment.RegistrationUserFragmentContract;
import com.incon.service.ui.register.fragment.RegistrationUserFragmentPresenter;
import com.incon.service.ui.validateotp.ValidateOtpContract;
import com.incon.service.ui.validateotp.ValidateOtpPresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements
        LoginContract.Presenter {

    private static final String TAG = LoginPresenter.class.getName();
    private Context appContext;
    private LoginDataManagerImpl loginDataManagerImpl;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
        loginDataManagerImpl = new LoginDataManagerImpl();
    }

    // login implemenatation
    @Override
    public void doLogin(LoginUserData loginUserData) {
        getView().showProgress(appContext.getString(R.string.progress_login));
        DisposableObserver<LoginResponse> observer = new DisposableObserver<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                saveLoginData(loginResponse);
                getView().navigateToHomePage(loginResponse);
            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                getView().navigateToHomePage(null);
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
            }

            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        AppApiService.getInstance().login(loginUserData).subscribe(observer);
        addDisposable(observer);
    }

    // validate otp implemenatation
    @Override
    public void validateOTP(HashMap<String, String> verify) {
        getView().showProgress(appContext.getString(R.string.validating_code));
        ValidateOtpPresenter otpPresenter = new ValidateOtpPresenter();
        otpPresenter.initialize(null);
        otpPresenter.setView(otpView);
        otpPresenter.validateOTP(verify);
    }

    @Override
    public void registerRequestOtp(String phoneNumber) {
        RegistrationUserFragmentPresenter registrationUserFragmentPresenter =
                new RegistrationUserFragmentPresenter();
        registrationUserFragmentPresenter.initialize(null);
        registrationUserFragmentPresenter.setView(registrationView);
        registrationUserFragmentPresenter.registerRequestOtp(phoneNumber);
    }

    RegistrationUserFragmentContract.View registrationView = new RegistrationUserFragmentContract
            .View() {

        @Override
        public void navigateToRegistrationActivityNext() {
            //DO nothing
        }

        @Override
        public void navigateToHomeScreen() {
            //DO nothing
        }

        @Override
        public void validateOTP() {
            //DO nothing
        }

        @Override
        public void showProgress(String message) {

        }

        @Override
        public void hideProgress() {

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

    ValidateOtpContract.View otpView = new ValidateOtpContract.View() {
        @Override
        public void validateOTP(LoginResponse loginResponse) {
            // save login data to shared preferences
            loginDataManagerImpl.saveLoginDataToPrefs(loginResponse);
            getView().hideProgress();
            getView().navigateToHomePage(loginResponse);
        }

        @Override
        public void validateWarrantyOTP(ValidateWarrantyOtpResponse warrantyOtpResponse) {
            //Do nothing
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

    public void saveLoginData(LoginResponse loginResponse) {
        // save login response to shared preferences
        loginDataManagerImpl.saveLoginDataToPrefs(loginResponse);
    }
}
