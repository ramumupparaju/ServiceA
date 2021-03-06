package com.incon.service.ui.status.base.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 1/2/2018.
 */

public class BaseOptionsPresenter extends BasePresenter<BaseOptionsContract.View> implements
        BaseOptionsContract.Presenter {

    private static final String TAG = BaseOptionsPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void getUsersListOfServiceCenters(int serviceCenterId) {
        getView().showProgress(appContext.getString(R.string.progress_loading_users_list));
        DisposableObserver<List<AddUser>> observer = new
                DisposableObserver<List<AddUser>>() {
                    @Override
                    public void onNext(List<AddUser> addUsers) {
                        BaseOptionsContract.View view = getView();
                        if (view != null) {
                            view.hideProgress();
                            view.loadUsersListOfServiceCenters(addUsers);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseOptionsContract.View view = getView();
                        Pair<Integer, String> errorDetails = ErrorMsgUtil.getErrorDetails(e);
                        if (view != null) {
                            view.hideProgress();
                            view.handleException(errorDetails);
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                };

        AppApiService.getInstance().getUsersListOfServiceCenterApi(serviceCenterId).subscribe(observer);
        addDisposable(observer);

    }

    //TODO have to remove
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
