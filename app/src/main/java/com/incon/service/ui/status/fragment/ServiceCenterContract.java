package com.incon.service.ui.status.fragment;

import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.servicerequest.ServiceRequest;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 12/6/2017.
 */

public interface ServiceCenterContract {

    interface View extends BaseView {
        void loadingNewServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponses);

        void loadUsersListOfServiceCenters(List<AddUser> usersListOfServiceCenters);

        void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse);
    }

    interface Presenter {
        void fetchServiceRequestsUsingRequestType(ServiceRequest serviceRequest, String loadingMessage);

        void getUsersListOfServiceCenters(int serviceCenterId);

        void upDateStatus(int userId, UpDateStatus upDateStatus);

    }
}
