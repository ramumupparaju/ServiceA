package com.incon.service.ui.status.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.servicerequest.ServiceRequest;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.status.base.base.BaseOptionsContract;
import com.incon.service.ui.status.base.base.BaseOptionsPresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 12/6/2017.
 */

public class ServiceCenterPresenter extends BasePresenter<ServiceCenterContract.View> implements
        ServiceCenterContract.Presenter {
    private static final String TAG = ServiceCenterPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void fetchServiceRequestsUsingRequestType(ServiceRequest serviceRequest, String loadingMessage) {
        getView().showProgress(loadingMessage);

        DisposableObserver<List<FetchNewRequestResponse>> observer = new DisposableObserver<List<FetchNewRequestResponse>>() {
            @Override
            public void onNext(List<FetchNewRequestResponse> fetchNewRequestResponses) {
                getView().loadingNewServiceRequests(fetchNewRequestResponses);
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

        AppApiService.getInstance().serviceRequestApi(serviceRequest).subscribe(observer);
        addDisposable(observer);
    }

    @Override
    public void getUsersListOfServiceCenters(int serviceCenterId) {

        BaseOptionsPresenter baseOptionsPresenter = new BaseOptionsPresenter();
        baseOptionsPresenter.initialize(null);
        baseOptionsPresenter.setView(new BaseOptionsContract.View() {
            @Override
            public void loadUsersListOfServiceCenters(List<AddUser> userList) {
                getView().loadUsersListOfServiceCenters(userList);

            }

            @Override
            public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {
                getView().loadUpDateStatus(upDateStatusResponse);

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
        baseOptionsPresenter.getUsersListOfServiceCenters(serviceCenterId);
    }

    @Override
    public void upDateStatus(int userId, UpDateStatus upDateStatus) {
        getView().showProgress(appContext.getString(R.string.progress_updating_status));
        DisposableObserver<UpDateStatusResponse> observer = new
                DisposableObserver<UpDateStatusResponse>() {
                    @Override
                    public void onNext(UpDateStatusResponse upDateStatusResponse) {
                        getView().loadUpDateStatus(upDateStatusResponse);
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

        AppApiService.getInstance().upDateStatus(userId, upDateStatus).subscribe(observer);
        addDisposable(observer);
    }

}
