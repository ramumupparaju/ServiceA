package com.incon.service.ui.status.base.base;

import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 1/2/2018.
 */

public interface BaseOptionsContract {
    interface View extends BaseView {
        void loadUsersListOfServiceCenters(List<AddUser> usersListOfServiceCenters);

        void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse );

    }

    interface Presenter {
        void getUsersListOfServiceCenters(int serviceCenterId);

        void upDateStatus(int userId, UpDateStatus upDateStatus);
    }
}
