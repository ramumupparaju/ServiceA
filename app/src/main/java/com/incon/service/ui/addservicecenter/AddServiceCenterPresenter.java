package com.incon.service.ui.addservicecenter;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.List;

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
    public void addingServiceCenter(int userId, AddServiceCenter addServiceCenter)
    {
        getView().showProgress(appContext.getString(R.string.progress_adding_service_center));

        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object categoriesList) {
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
        AppApiService.getInstance().addServiceCenter(userId,addServiceCenter).subscribe(observer);
        addDisposable(observer);
    }


}
