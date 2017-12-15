package com.incon.service.ui.user.registration.fragment;



import com.incon.service.dto.registration.Registration;
import com.incon.service.ui.BaseView;

import java.util.HashMap;

/**
 * Created on 08 Jun 2017 6:32 PM.
 *
 */
public class RegistrationServiceUserFragmentContract {

    public interface View extends BaseView {
        void navigateToRegistrationActivityNext();
        void navigateToHomeScreen();
        void validateOTP();

    }

    interface Presenter {
        void register(Registration registrationBody);
        void validateOTP(HashMap<String, String> verify);
        void registerRequestOtp(String phoneNumber);
        void registerRequestPasswordOtp(String phoneNumber);
    }

}
