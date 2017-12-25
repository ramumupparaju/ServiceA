package com.incon.service.ui.settings.service;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 12/19/2017.
 */

public class AllServiceCentersPresenter extends BasePresenter<AllServiceCentersContract.View>
        implements AllServiceCentersContract.Presenter {
    private static final String TAG = AllServiceCentersPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void serviceCentersList(int userId) {
        getView().showProgress(appContext.getString(R.string.progress_loading_service_centers));
        DisposableObserver<List<ServiceCenterResponse>> observer = new
                DisposableObserver<List<ServiceCenterResponse>>() {
                    @Override
                    public void onNext(List<ServiceCenterResponse> serviceCenterResponse) {
                        getView().loadServiceCentersList(serviceCenterResponse);
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
        AppApiService.getInstance().getServiceCentersApi(userId).subscribe(observer);
        addDisposable(observer);
    }
}
