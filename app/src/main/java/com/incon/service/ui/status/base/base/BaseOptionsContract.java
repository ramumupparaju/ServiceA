package com.incon.service.ui.status.base.base;

import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 1/2/2018.
 */

public interface BaseOptionsContract {
    interface View extends BaseView {
        void loadUsersListOfServiceCenters(List<AddUser> usersListOfServiceCenters);

    }

    interface Presenter {
        void getUsersListOfServiceCenters(int serviceCenterId);
    }
}
