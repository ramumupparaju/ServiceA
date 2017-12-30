package com.incon.service.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.apimodel.components.getstatuslist.DefaultStatusData;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.register.RegistrationContract;
import com.incon.service.ui.register.RegistrationPresenter;
import com.incon.service.ui.settings.service.AllServiceCentersContract;
import com.incon.service.ui.settings.service.AllServiceCentersPresenter;
import com.incon.service.ui.validateotp.ValidateOtpPresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created on 31 May 2017 11:19 AM.
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements
        HomeContract.Presenter {

    private static final String TAG = HomePresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    // get status list
    @Override
    public void getDefaultStatusData() {
        DisposableObserver<List<DefaultStatusData>> observer = new DisposableObserver<List<DefaultStatusData>>() {
            @Override
            public void onNext(List<DefaultStatusData> statusListResponses) {
                ConnectApplication.getAppContext().setDefaultStausData(statusListResponses);
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
        AppApiService.getInstance().getStatusList().subscribe(observer);
        addDisposable(observer);
    }

    @Override
    public void getServiceCenters(int userId) {
        AllServiceCentersPresenter allServiceCentersPresenter = new AllServiceCentersPresenter();
        allServiceCentersPresenter.initialize(null);
        allServiceCentersPresenter.setView(new AllServiceCentersContract.View() {
            @Override
            public void loadServiceCentersList(List<ServiceCenterResponse> serviceCenterResponseList) {
                ConnectApplication.getAppContext().setServiceCenterList(serviceCenterResponseList);
                getView().hideProgress();
            }

            @Override
            public void showProgress(String message) {
                getView().showProgress(message);
            }

            @Override
            public void hideProgress() {
                getView().hideProgress();
            }

            @Override
            public void showErrorMessage(String errorMessage) {
                getView().showErrorMessage(errorMessage);
            }

            @Override
            public void handleException(Pair<Integer, String> error) {
                getView().handleException(error);
            }
        });
        allServiceCentersPresenter.serviceCentersList(userId);
    }
}

