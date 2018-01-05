package com.incon.service.ui.status;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BasePresenter;
import com.incon.service.ui.status.base.base.BaseOptionsContract;
import com.incon.service.ui.status.base.base.BaseOptionsPresenter;

import java.util.List;

/**
 * Created by PC on 12/6/2017.
 */

public class StatusTabPresenter extends BasePresenter<StatusTabContract.View> implements
        StatusTabContract.Presenter {
    private static final String TAG = StatusTabPresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }


    @Override
    public void getUsersListOfServiceCenters(int serviceCenterId) {
        final BaseOptionsPresenter baseOptionsPresenter = new BaseOptionsPresenter();
        baseOptionsPresenter.initialize(null);
        baseOptionsPresenter.setView(new BaseOptionsContract.View() {
            @Override
            public void loadUsersListOfServiceCenters(List<AddUser> userList) {
                getView().loadUsersListOfServiceCenters(userList);
                baseOptionsPresenter.disposeAll();
            }

            @Override
            public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {

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
                baseOptionsPresenter.disposeAll();
            }
        });
        baseOptionsPresenter.getUsersListOfServiceCenters(serviceCenterId);
    }

}
