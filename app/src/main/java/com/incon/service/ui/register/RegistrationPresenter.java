package com.incon.service.ui.register;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.AppConstants;
import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.defaults.DefaultsResponse;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;
import com.incon.service.utils.OfflineDataManager;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class RegistrationPresenter extends BasePresenter<RegistrationContract.View> implements
        RegistrationContract.Presenter {

    private static final String TAG = RegistrationPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    /**
     * Uploading defaults data for registration
     */
    //default api implemenatation
    @Override
    public void defaultsApi() {
        getView().showProgress(appContext.getString(R.string.progress_defaults));
        DisposableObserver<List<FetchCategories>> observer = new DisposableObserver<List<FetchCategories>>() {
            @Override
            public void onNext(List<FetchCategories> fetchCategoriesList) {
                getView().hideProgress();
                ConnectApplication.getAppContext().setFetchCategoriesList(fetchCategoriesList);
                getView().startRegistration(true);
            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgress();
                Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                getView().handleException(errorDetails);
                if (errorDetails.first == AppConstants.ErrorCodes.NO_NETWORK) {
                    if (ConnectApplication.getAppContext().getFetchCategoriesList() != null) {
                        getView().startRegistration(true);
                        return;
                    }
                }
                getView().startRegistration(false);
            }

            @Override
            public void onComplete() {
            }
        };
        AppApiService.getInstance().defaultsApi().subscribe(observer);
        addDisposable(observer);
    }

}
