package com.incon.service.ui.status.fragment;

import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 12/6/2017.
 */

public interface NewRequestContract {

    interface View extends BaseView {
        void loadingNewServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponses);
        void loadUsersListOfServiceCenters(List<AddUser> usersListOfServiceCenters);
    }
    interface Presenter {
        void fetchNewServiceRequests(int userId);
        void getUsersListOfServiceCenters(int serviceCenterId);

    }
}
