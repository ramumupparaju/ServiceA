package com.incon.service.ui.settings.service;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.AppConstants;
import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

import static com.incon.service.AppConstants.DEFAULT_VALUE;

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
        if (userId == DEFAULT_VALUE) {
            userId = SharedPrefsUtils.loginProvider().getIntegerPreference(AppConstants.LoginPrefs.USER_ID, DEFAULT_VALUE);
        }
        getView().showProgress(appContext.getString(R.string.progress_loading_service_centers));
        DisposableObserver<List<AddServiceCenter>> observer = new
                DisposableObserver<List<AddServiceCenter>>() {
                    @Override
                    public void onNext(List<AddServiceCenter> serviceCenterResponse) {
                        getView().loadServiceCentersList(serviceCenterResponse);
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
        AppApiService.getInstance().getServiceCentersApi(userId).subscribe(observer);
        addDisposable(observer);
    }
}
