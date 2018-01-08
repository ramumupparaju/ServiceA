package com.incon.service.ui.adduser;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.update.UpDateUserProfile;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.settings.update.UpDateUserProfileContract;
import com.incon.service.ui.settings.update.UpDateUserProfilePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public class AddUserPresenter extends BasePresenter<AddUserContract.View>
        implements AddUserContract.Presenter {
    private static final String TAG = AddUserPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    @Override
    public void addingUser(int userId, AddUser addUser) {
        getView().showProgress(appContext.getString(R.string.progress_adding_user));
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object categoriesList) {
                getView().userAddedSuccessfully();
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
        AppApiService.getInstance().addUser(userId, addUser).subscribe(observer);
        addDisposable(observer);

    }

    @Override
    public void deleteUser(int serviceCenterId) {

        getView().showProgress(appContext.getString(R.string.progress_delete_user));
        DisposableObserver<Object> observer = new DisposableObserver<Object>() {
            @Override
            public void onNext(Object o) {
                getView().userDeleteSuccessfully();
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
        AppApiService.getInstance().deleteUserApi(serviceCenterId).subscribe(observer);
        addDisposable(observer);

    }

    @Override
    public void upDateUserProfile(int userId, UpDateUserProfile upDateUserProfile) {
        getView().showProgress(appContext.getString(R.string.progress_update_user));
        UpDateUserProfilePresenter upDateUserProfilePresenter =  new UpDateUserProfilePresenter();
        upDateUserProfilePresenter.initialize(null);
        upDateUserProfilePresenter.setView(new UpDateUserProfileContract.View() {
            @Override
            public void loadUpDateUserProfileResponce(LoginResponse loginResponse) {
                getView().loadUpDateUserProfileResponce(loginResponse);
                getView().hideProgress();

            }

            @Override
            public void showProgress(String message) {
                getView().showProgress(message);
            }

            @Override
            public void hideProgress() {
                getView().hideProgress();

            }

            @Override
            public void showErrorMessage(String errorMessage) {
                getView().showErrorMessage(errorMessage);
            }

            @Override
            public void handleException(Pair<Integer, String> error) {
                getView().handleException(error);
            }
        });


    }


}

