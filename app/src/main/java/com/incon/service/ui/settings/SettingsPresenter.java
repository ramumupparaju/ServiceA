package com.incon.service.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.custom.exception.NoConnectivityException;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.register.RegistrationContract;
import com.incon.service.ui.register.RegistrationPresenter;
import com.incon.service.utils.ErrorMsgUtil;


/**
 * Created on 31 May 2017 11:19 AM.
 *
 */
public class SettingsPresenter extends BasePresenter<SettingsContract.View> implements
        SettingsContract.Presenter {

    private Context appContext;
    private static final String TAG = SettingsPresenter.class.getName();

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void getDefaultsApi() {
        RegistrationPresenter registrationPresenter = new RegistrationPresenter();
        registrationPresenter.initialize(null);
        registrationPresenter.setView(registrationView);

        registrationPresenter.defaultsApi();

    }

    RegistrationContract.View registrationView = new RegistrationContract.View() {
        @Override
        public void navigateToNext() {

        }

        @Override
        public void navigateToBack() {

        }

        @Override
        public void startRegistration(boolean isDataAvailable) {
            if (isDataAvailable)
                getView().loadDefaultsData();
            else {
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(new NoConnectivityException());
                getView().handleException(errorDetails);
            }
        }

        @Override
        public void showProgress(String message) {

        }

        @Override
        public void hideProgress() {

        }

        @Override
        public void showErrorMessage(String errorMessage) {

        }

        @Override
        public void handleException(Pair<Integer, String> error) {

        }
    };
}
