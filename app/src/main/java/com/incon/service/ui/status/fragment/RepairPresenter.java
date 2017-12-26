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

import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 12/6/2017.
 */

public class RepairPresenter extends BasePresenter<RepairContract.View> implements
        RepairContract.Presenter {
    private static final String TAG = RepairPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }
    @Override
    public void fetchNewServiceRequests(int userId) {
        getView().showProgress(appContext.getString(R.string.progress_fetch_new_service_request));
        DisposableObserver<List<FetchNewRequestResponse>> observer = new DisposableObserver<List<FetchNewRequestResponse>>() {
            @Override
            public void onNext(List<FetchNewRequestResponse> fetchNewRequestResponses) {
                getView().fetchNewServiceRequests(fetchNewRequestResponses);
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
        AppApiService.getInstance().fetchNewServiceRequestApi(userId).subscribe(observer);

    }

}
