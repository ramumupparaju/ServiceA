package com.incon.service.ui.changepassword;


import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.ui.BaseView;

import java.util.HashMap;

public interface ChangePasswordContract {

    interface View extends BaseView {
        void navigateToLoginPage(LoginResponse loginResponse);
    }

    interface Presenter {
        void changePassword(HashMap<String, String> password);
        void saveUserData(LoginResponse loginResponse);
    }

}
