package com.incon.service.ui.login;



import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.dto.login.LoginUserData;
import com.incon.service.ui.BaseView;

import java.util.HashMap;

public interface LoginContract {

    interface View extends BaseView {
        void navigateToHomePage(LoginResponse loginResponse);
    }

    interface Presenter {
        void doLogin(LoginUserData loginUserData);
        void validateOTP(HashMap<String, String> verify);
        void registerRequestOtp(String phoneNumber);
    }

}
