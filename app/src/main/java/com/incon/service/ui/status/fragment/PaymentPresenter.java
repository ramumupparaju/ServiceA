package com.incon.service.ui.status.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;

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
    @Override
    public void fetchPaymentServiceRequests(int userId) {
        getView().showProgress(appContext.getString(R.string.progress_fetch_payment_service_request));

        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().fetchPaymentServiceRequests(o);
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
        AppApiService.getInstance().fetchPaymentServiceRequestApi(userId).subscribe(observer);


    }
}
