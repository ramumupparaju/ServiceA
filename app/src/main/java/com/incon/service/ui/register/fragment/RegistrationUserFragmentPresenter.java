package com.incon.service.ui.register.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.service.dto.registration.Registration;
import com.incon.service.login.LoginDataManagerImpl;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.validateotp.ValidateOtpContract;
import com.incon.service.ui.validateotp.ValidateOtpPresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;


public class RegistrationUserFragmentPresenter extends
        BasePresenter<RegistrationUserFragmentContract.View> implements
        RegistrationUserFragmentContract.Presenter {


    private Context appContext;
    private static final String TAG = RegistrationUserFragmentPresenter.class.getName();
    private LoginDataManagerImpl loginDataManagerImpl;


}
