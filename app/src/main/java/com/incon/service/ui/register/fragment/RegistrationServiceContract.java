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
       // void uploadStoreLogo(int storeId);
        void validateOTP();
        void loadCategoriesList(List<FetchCategories> categoriesList);
    }

    interface Presenter {
        void register(Registration registrationBody);
       // void uploadStoreLogo(int storeId, MultipartBody.Part storeLogo);
        void validateOTP(HashMap<String, String> verify);
        void registerRequestOtp(String phoneNumber);
        void registerRequestPasswordOtp(String phoneNumber);
        void getCategories(int merchantId);
    }

}
