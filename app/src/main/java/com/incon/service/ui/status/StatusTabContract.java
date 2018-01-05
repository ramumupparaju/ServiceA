package com.incon.service.ui.status;

import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 12/6/2017.
 */

public interface StatusTabContract {

    interface View extends BaseView {
        void loadUsersListOfServiceCenters(List<AddUser> usersListOfServiceCenters);
    }

    interface Presenter {
        void getUsersListOfServiceCenters(int serviceCenterId);
    }
}
