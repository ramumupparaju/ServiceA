package com.incon.service.ui.addservicecenter;

import android.content.Context;
import android.util.Pair;

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
    public void getCategories(int merchantId) {
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object categoriesList) {
                getView().loadCategoriesList((List<FetchCategories>) categoriesList);

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
        AppApiService.getInstance().getCategories(merchantId).subscribe(observer);
        addDisposable(observer);

    }

    @Override
    public void addingserviceCenter(int userId, AddServiceCenter addServiceCenter)
    {

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
        AppApiService.getInstance().addServiceCenter(userId,addServiceCenter);
        addDisposable(observer);
    }
}
