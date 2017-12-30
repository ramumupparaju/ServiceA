package com.incon.service.ui.adduser;

import com.incon.service.apimodel.components.fetchdesignations.FetchDesignationsResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public interface AddUserContract {
    interface View extends BaseView {
    }

    interface Presenter {
        void addingUser(int userId, AddUser addUser);
    }
}
