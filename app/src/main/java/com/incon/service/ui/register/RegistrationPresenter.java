package com.incon.service.ui.register;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.AppConstants;
import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.defaults.DefaultsResponse;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;
import com.incon.service.utils.OfflineDataManager;

import io.reactivex.observers.DisposableObserver;

public class RegistrationPresenter extends BasePresenter<RegistrationContract.View> implements
        RegistrationContract.Presenter {

    private static final String TAG = RegistrationPresenter.class.getName();
    private Context appContext;
    private OfflineDataManager offlineDataManager;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
        offlineDataManager = new OfflineDataManager();
    }

    /**
     * Uploading defaults data for registration
     */
    //default api implemenatation
    @Override
    public void defaultsApi() {
        getView().showProgress(appContext.getString(R.string.progress_defaults));
        DisposableObserver<DefaultsResponse> observer = new DisposableObserver<DefaultsResponse>() {
            @Override
            public void onNext(DefaultsResponse defaultsResponse) {
                getView().hideProgress();
                offlineDataManager.saveData(defaultsResponse,
                        DefaultsResponse.class.getName());
                getView().startRegistration(true);
            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
                if (errorDetails.first == AppConstants.ErrorCodes.NO_NETWORK) {
                    DefaultsResponse defaultsResponse = offlineDataManager
                            .loadData(DefaultsResponse.class,
                                    DefaultsResponse.class.getName());
                    if (defaultsResponse != null) {
                        getView().startRegistration(true);
                        return;
                    }
                }
                getView().startRegistration(false);
            }

            @Override
            public void onComplete() {
                getView().hideProgress();
            }
        };
        AppApiService.getInstance().defaultsApi().subscribe(observer);
        addDisposable(observer);
    }

}
