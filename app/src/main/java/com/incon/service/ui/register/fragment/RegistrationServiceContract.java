package com.incon.service.ui.register.fragment;



import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.dto.registration.Registration;
import com.incon.service.ui.BaseView;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created on 08 Jun 2017 6:32 PM.
 *
 */
public class RegistrationServiceContract {

    public interface View extends BaseView {
        void navigateToRegistrationActivityNext();
        void navigateToHomeScreen();
        void uploadServiceCenterLogo(int serviceCenterId);
        void navigateToLoginScreen();
    }

    interface Presenter {
        void register(Registration registrationBody);
        void uploadServiceCenterLogo(int serviceCenterId, MultipartBody.Part serviceCenterLogo);
    }

}
