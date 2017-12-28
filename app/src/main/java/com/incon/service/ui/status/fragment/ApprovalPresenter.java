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

public class ApprovalPresenter extends BasePresenter<ApprovalContract.View> implements
        ApprovalContract.Presenter {
    private static final String TAG = ApprovalPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }
    @Override
    public void fetchApprovalServiceRequests(int userId) {

        getView().showProgress(appContext.getString(R.string.progress_fetch_approval_service_request));
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().fetchApprovalServiceRequests(o);
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
        AppApiService.getInstance().fetchApprovalServiceRequestApi(userId).subscribe(observer);


    }
}
