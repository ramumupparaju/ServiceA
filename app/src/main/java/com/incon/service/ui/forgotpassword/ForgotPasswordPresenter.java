package com.incon.service.ui.forgotpassword;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;


import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.HashMap;

import io.reactivex.observers.DisposableObserver;

public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordContract.View> implements
        ForgotPasswordContract.Presenter {

    private static final String TAG = ForgotPasswordPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void forgotPassword(HashMap<String, String> phoneNumber) {
        getView().showProgress(appContext.getString(R.string.progress_forgotpassword));
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().navigateToResetPromtPage();
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
        AppApiService.getInstance().forgotPassword(phoneNumber).subscribe(observer);
        addDisposable(observer);
    }
}
