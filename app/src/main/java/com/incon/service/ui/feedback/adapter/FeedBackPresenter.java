package com.incon.service.ui.feedback.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.productinforesponse.ProductInfoResponse;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.status.fragment.CheckUpContract;
import com.incon.service.ui.status.fragment.CheckUpPresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by shiva on 12/8/2017.
 */

public class FeedBackPresenter  extends BasePresenter<FeedBackContract.View> implements
        FeedBackContract.Presenter {
    private static final String TAG = FeedBackPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }


    public void returnHistory(int userId) {
        getView().showProgress(appContext.getString(R.string.progress_return_history));
        DisposableObserver<List<ProductInfoResponse>> observer = new
                DisposableObserver<List<ProductInfoResponse>>() {
                    @Override
                    public void onNext(List<ProductInfoResponse> historyResponse) {
                        getView().loadReturnHistory(historyResponse);
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
        AppApiService.getInstance().returnApi(userId).subscribe(observer);
        addDisposable(observer);
    }

}
