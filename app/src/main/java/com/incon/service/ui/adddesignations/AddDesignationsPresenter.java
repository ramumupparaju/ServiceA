package com.incon.service.ui.adddesignations;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;
import com.incon.service.dto.adddesignation.AddDesignation;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.validateotp.ValidateOtpPresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 12/19/2017.
 */

public class AddDesignationsPresenter extends BasePresenter<AddDesignationsContract.View>
        implements AddDesignationsContract.Presenter {
    private static final String TAG = AddDesignationsPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void addDesignations(int userId, AddDesignation addDesignation) {
        getView().showProgress(appContext.getString(R.string.progress_adding_designation));
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
        AppApiService.getInstance().addDesignation(userId, addDesignation).subscribe(observer);
        addDisposable(observer);
    }

}
