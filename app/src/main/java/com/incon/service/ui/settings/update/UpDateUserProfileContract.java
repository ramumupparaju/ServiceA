package com.incon.service.ui.settings.update;

import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.dto.update.UpDateUserProfile;
import com.incon.service.ui.BaseView;

/**
 * Created by PC on 10/13/2017.
 */

public interface UpDateUserProfileContract {

    interface View extends BaseView {
        void loadUpDateUserProfileResponce(LoginResponse loginResponse);

    }
    interface Presenter {
        void upDateUserProfile(int userId, UpDateUserProfile upDateUserProfile);
        void saveUserData(LoginResponse loginResponse);

    }
}
