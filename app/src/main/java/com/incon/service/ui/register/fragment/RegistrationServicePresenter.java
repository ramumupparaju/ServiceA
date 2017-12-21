package com.incon.service.ui.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.service.dto.registration.Registration;
import com.incon.service.login.LoginDataManagerImpl;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.validateotp.ValidateOtpContract;
import com.incon.service.ui.validateotp.ValidateOtpPresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.HashMap;
import java.util.List;

import io.reactivex.observers.DisposableObserver;


public class RegistrationServicePresenter extends
        BasePresenter<RegistrationServiceContract.View> implements
        RegistrationServiceContract.Presenter {

    private static final String TAG = RegistrationServicePresenter.class.getName();
    private Context appContext;
    private LoginDataManagerImpl loginDataManagerImpl;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
        loginDataManagerImpl = new LoginDataManagerImpl();
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





    public void saveMerchantInfo(LoginResponse loginResponse) {
        // save login data to shared preferences
        loginDataManagerImpl.saveLoginDataToPrefs(loginResponse);
    }

}
