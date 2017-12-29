package com.incon.service.ui.settings.userdesignation;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.adddesignation.DesignationResponse;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.apimodel.components.userslistofservicecenters.UsersListOfServiceCenters;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by PC on 12/19/2017.
 */

public class AllUsersDesignationsPresenter extends BasePresenter<AllUsersDesignationsContract.View>
        implements AllUsersDesignationsContract.Presenter {
    private static final String TAG = AllUsersDesignationsPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void doUsersDesignationsApi(int userId, int serviceCenterId) {
        getView().showProgress(appContext.getString(R.string.progress_loading_data));

        Observable<List<UsersListOfServiceCenters>> userListObservable = getUserListObservable(userId);
        Observable<List<DesignationResponse>> designationsListObservable = getDesignationListObservable(userId, serviceCenterId);

        Observable<String> zip = Observable.zip(userListObservable, designationsListObservable, new BiFunction<List<UsersListOfServiceCenters>, List<DesignationResponse>, String>() {
            @Override
            public String apply(@NonNull List<UsersListOfServiceCenters> usersList, @NonNull List<DesignationResponse> designationsList) throws Exception {

                getView().loadUsersDesignationsList(usersList, designationsList);
                return "";
            }
        });

        DisposableObserver<String> observer = new
                DisposableObserver<String>() {
                    @Override
                    public void onNext(String usersListOfServiceCenters) {
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
        zip.subscribe(observer);
        addDisposable(observer);
    }

    private Observable<List<DesignationResponse>> getDesignationListObservable(int userId, int serviceCenterId) {
        return AppApiService.getInstance().getDesignationsListUsingServiceCenter(userId, serviceCenterId);
    }

    private Observable<List<UsersListOfServiceCenters>> getUserListObservable(int userId) {
        return AppApiService.getInstance().getUsersListOfServiceCenterApi(userId);
    }
}


