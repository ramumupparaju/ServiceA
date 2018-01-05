package com.incon.service.ui.addservicecenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.updateservicecenter.UpDateServiceCenterResponse;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.dto.updateservicecenter.UpDateServiceCenter;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.register.RegistrationContract;
import com.incon.service.ui.register.RegistrationPresenter;
import com.incon.service.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public class AddServiceCenterPresenter extends BasePresenter<AddServiceCenterContract.View>
        implements AddServiceCenterContract.Presenter {

    private static final String TAG = AddServiceCenterPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }


    @Override
    public void defaultsApi() {
        RegistrationPresenter registrationPresenter = new RegistrationPresenter();
        registrationPresenter.initialize(null);
        registrationPresenter.setView(new RegistrationContract.View() {
            @Override
            public void navigateToNext() {
                //do nothing
            }

            @Override
            public void navigateToBack() {
                //do nothing
            }

            @Override
            public void startRegistration(boolean isDataAvailable) {
                getView().loadedDefaultsData(isDataAvailable);
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
        registrationPresenter.defaultsApi();
    }

    @Override
    public void addingServiceCenter(int userId, AddServiceCenter addServiceCenter) {
        getView().showProgress(appContext.getString(R.string.progress_adding_service_center));

        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object categoriesList) {
                getView().serviceCenterAddedSuccessfully();
                getView().hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
            }

            @Override
            public void onComplete() {

            }
        };
        AppApiService.getInstance().addServiceCenter(userId, addServiceCenter).subscribe(observer);
        addDisposable(observer);

    }

    @Override
    public void updateServiceCenter(int serviceCenterId, UpDateServiceCenter upDateServiceCenter) {

        getView().showProgress(appContext.getString(R.string.progress_update_service_center));

        DisposableObserver<UpDateServiceCenterResponse> observer = new
                DisposableObserver<UpDateServiceCenterResponse>() {
                    @Override
                    public void onNext(UpDateServiceCenterResponse upDateServiceCenterResponse) {
                        getView().hideProgress();
                        getView().loadUpDateServiceCenterResponce(upDateServiceCenterResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideProgress();
                        Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                        getView().handleException(errorDetails);
                    }

                    @Override
                    public void onComplete() {

                    }
                };
        AppApiService.getInstance().upDateServiceCenter(serviceCenterId, upDateServiceCenter).
                subscribe(observer);
        addDisposable(observer);


    }

    @Override
    public void deleteServiceCenter(int serviceCenterId) {
        getView().showProgress(appContext.getString(R.string.progress_delete_service_center));
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().serviceCenterDeleteSuccessfully();
                getView().hideProgress();

            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
            }

            @Override
            public void onComplete() {

            }
        };
        AppApiService.getInstance().deleteServiceCenterApi(serviceCenterId).subscribe(observer);
        addDisposable(observer);

    }



}
