package com.incon.service.ui.status.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.List;

import io.reactivex.Observable;
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
    public void fetchApprovalServiceRequests(int servicerCenterId,int userId) {

        getView().showProgress(appContext.getString(R.string.progress_fetch_approval_service_request));
        DisposableObserver<List<FetchNewRequestResponse>> observer = new DisposableObserver<List<FetchNewRequestResponse>>() {
            @Override
            public void onNext(List<FetchNewRequestResponse> fetchNewRequestResponses) {
                getView().loadingApprovalServiceRequests(fetchNewRequestResponses);
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
        Observable<List<FetchNewRequestResponse>> listObservable;
        if (userId == -1) {
            listObservable = AppApiService.getInstance().fetchApprovalServiceRequestApi(servicerCenterId);
        } else {
            listObservable = AppApiService.getInstance().fetchApprovalAssignedRequestApi(userId);
        }
        listObservable.subscribe(observer);
        addDisposable(observer);
    }
}
