package com.incon.service.ui.adduser;

import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BaseView;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public interface AddUserContract {
    interface View extends BaseView {
        void userAddedSuccessfully();
    }

    interface Presenter {
        void addingUser(int userId, AddUser addUser);
    }
}
