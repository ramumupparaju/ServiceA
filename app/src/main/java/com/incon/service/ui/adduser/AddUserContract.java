package com.incon.service.ui.adduser;

import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.update.UpDateUserProfile;
import com.incon.service.ui.BaseView;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public interface AddUserContract {
    interface View extends BaseView {
        void userAddedSuccessfully();
        void userDeleteSuccessfully();
        void loadUpDateUserProfileResponce(LoginResponse loginResponse);
    }

    interface Presenter {
        void addingUser(int userId, AddUser addUser);
        void deleteUser(int serviceCenterId);
        void upDateUserProfile(int userId, UpDateUserProfile upDateUserProfile);
    }
}
