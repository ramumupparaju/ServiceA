package com.incon.service.ui.adddesignations;

import android.content.Context;
import android.util.Pair;

import com.incon.service.api.AppApiService;
import com.incon.service.dto.adddesignation.AddDesignation;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.addservicecenter.AddServiceCenterPresenter;
import com.incon.service.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 12/19/2017.
 */

public class AddDesignationsPresenter extends BasePresenter<AddDesignationsContract.View>
        implements AddDesignationsContract.Presenter {
    private static final String TAG = AddDesignationsPresenter.class.getName();
    private Context appContext;

    @Override
    public void addDesignations(int userId, AddDesignation addDesignation) {


        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object categoriesList) {

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
        AppApiService.getInstance().addDesignation(userId,addDesignation);
        addDisposable(observer);
    }
}
