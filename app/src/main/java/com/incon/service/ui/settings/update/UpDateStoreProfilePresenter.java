package com.incon.service.ui.settings.update;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.api.AppApiService;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.dto.update.UpDateStoreProfile;
import com.incon.service.login.LoginDataManagerImpl;
import com.incon.service.ui.BasePresenter;
import com.incon.service.utils.ErrorMsgUtil;

import io.reactivex.observers.DisposableObserver;


/**
 * Created by PC on 10/13/2017.
 */

public class UpDateStoreProfilePresenter extends BasePresenter<UpDateStoreProfileContract.View>
        implements UpDateStoreProfileContract.Presenter {
    private static final String TAG = UpDateStoreProfilePresenter.class.getName();
    private Context appContext;

    @Override
    public void initialize(Bundle extras) {
        super.initialize(extras);
        appContext = ConnectApplication.getAppContext();
    }

    // upDate store profile implemenatation
    public void upDateStoreProfile(int merchantId, UpDateStoreProfile upDateStoreProfile) {
        getView().showProgress(appContext.getString(R.string.progress_updateuserprofile));
        DisposableObserver<LoginResponse> observer = new
                DisposableObserver<LoginResponse>() {
                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        getView().hideProgress();
                        getView().loadUpDateStoreProfileResponce(loginResponse);
                        saveUserStoreData(loginResponse);
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
        AppApiService.getInstance().upDateStoreProfile(merchantId, upDateStoreProfile).
                subscribe(observer);
        addDisposable(observer);

    }

    @Override
    public void saveUserStoreData(LoginResponse loginResponse) {
        LoginDataManagerImpl loginDataManager = new LoginDataManagerImpl();
        loginDataManager.saveLoginDataToPrefs(loginResponse);

    }
}
