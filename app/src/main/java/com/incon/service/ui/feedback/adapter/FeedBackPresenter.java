package com.incon.service.ui.feedback.adapter;

import android.content.Context;
import android.os.Bundle;

import com.incon.service.ConnectApplication;
import com.incon.service.ui.BasePresenter;

/**
 * Created by shiva on 12/8/2017.
 */

public class FeedBackPresenter  extends BasePresenter<FeedBackContract.View> implements
        FeedBackContract.Presenter {
    private static final String TAG = FeedBackPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }




}
