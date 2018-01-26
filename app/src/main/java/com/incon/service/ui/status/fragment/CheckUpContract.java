package com.incon.service.ui.status.fragment;

import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 12/6/2017.
 */

public interface CheckUpContract {

    interface View extends BaseView {
        void loadingCheckUpRequests(List<FetchNewRequestResponse> fetchNewRequestResponses);

        void loadUsersListOfServiceCenters(List<AddUser> usersListOfServiceCenters);

        void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse);
    }

    interface Presenter {
        void fetchCheckUpServiceRequests(int servicerCenterId, int userId);

        void getUsersListOfServiceCenters(int serviceCenterId);

        void upDateStatus(int userId, UpDateStatus upDateStatus);

    }
}
