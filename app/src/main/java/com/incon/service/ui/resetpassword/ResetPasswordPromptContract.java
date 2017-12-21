package com.incon.service.ui.resetpassword;


import com.incon.service.ui.BaseView;

import java.util.HashMap;

/**
 * Created on 08 Jun 2017 6:32 PM.
 */
public class ResetPasswordPromptContract {

    public interface View extends BaseView {
        void validateOtp();

    }

    interface Presenter {

        void doRequestOtpApi(HashMap<String, String> verifyOTP);

        void doResendOtpApi(String phoneNumber);
    }

}
