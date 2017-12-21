package com.incon.service.ui.register.fragment;

import android.content.Context;

import com.incon.service.login.LoginDataManagerImpl;
import com.incon.service.ui.BasePresenter;


public class RegistrationUserPresenter extends
        BasePresenter<RegistrationUserContract.View> implements
        RegistrationUserContract.Presenter {


    private Context appContext;
    private static final String TAG = RegistrationUserPresenter.class.getName();
    private LoginDataManagerImpl loginDataManagerImpl;


}
