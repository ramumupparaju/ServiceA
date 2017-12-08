package com.incon.service.ui.splash;

import android.os.Bundle;

import com.incon.service.ui.BasePresenter;


public class SplashPresenter extends BasePresenter<SplashContract.View> implements
        SplashContract.Presenter {

    private static final String TAG = SplashPresenter.class.getName();

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        getView().navigateToMainScreen();
    }

}
