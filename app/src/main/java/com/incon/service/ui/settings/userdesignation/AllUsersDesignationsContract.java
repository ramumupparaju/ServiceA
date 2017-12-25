package com.incon.service.ui.settings.userdesignation;

import com.incon.service.apimodel.components.userslistofservicecenters.UsersListOfServiceCenters;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 12/19/2017.
 */

public interface AllUsersDesignationsContract {
    interface View extends BaseView {

        void loadUsersDesignationsList(List<UsersListOfServiceCenters> usersListOfServiceCenters);


    }
    interface Presenter {
        void doUsersDesignationsApi(int userId, int serviceCenterId);
    }
}
