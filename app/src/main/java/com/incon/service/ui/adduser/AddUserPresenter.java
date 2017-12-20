package com.incon.service.ui.adduser;

import android.content.Context;
import android.util.Pair;

import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.fetchdesignationsresponse.FetchDesignationsResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public class AddUserPresenter  extends BasePresenter<AddUserContract.View>
        implements AddUserContract.Presenter {
    private static final String TAG = AddUserPresenter.class.getName();
    private Context appContext;


    @Override
    public void addingUser(int userId, AddUser addUser) {
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
        AppApiService.getInstance().addUser(userId,addUser);
        addDisposable(observer);

    }

    @Override
    public void fetchDesignations(int serviceCenterId, int userId) {

        getView().showProgress(appContext.getString(R.string.progress_designations));
        DisposableObserver<FetchDesignationsResponse> observer = new DisposableObserver<FetchDesignationsResponse>() {
            @Override
            public void onNext(FetchDesignationsResponse fetchDesignationsResponse) {
               getView().loadFetchDesignations(fetchDesignationsResponse);
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
        AppApiService.getInstance().fetchDesignations(serviceCenterId,userId);
        addDisposable(observer);

    }
}

