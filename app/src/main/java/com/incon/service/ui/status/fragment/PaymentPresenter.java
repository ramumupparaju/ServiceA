package com.incon.service.ui.status.fragment;

import android.content.Context;
import android.os.Bundle;

import com.incon.service.ConnectApplication;
import com.incon.service.ui.BasePresenter;

/**
 * Created by PC on 12/6/2017.
 */

public class PaymentPresenter extends BasePresenter<PaymentContract.View> implements
        PaymentContract.Presenter {
    private static final String TAG = PaymentPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }




}