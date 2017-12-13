package com.incon.service.ui.settings.update;


import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.dto.update.UpDateStoreProfile;
import com.incon.service.ui.BaseView;

/**
 * Created by PC on 10/13/2017.
 */

public interface UpDateStoreProfileContract {
    interface View extends BaseView {
        void loadUpDateStoreProfileResponce(LoginResponse loginResponse);
    }

    interface Presenter {
        void upDateStoreProfile(int merchantId, UpDateStoreProfile upDateStoreProfile);
        void saveUserStoreData(LoginResponse loginResponse);

    }
}
