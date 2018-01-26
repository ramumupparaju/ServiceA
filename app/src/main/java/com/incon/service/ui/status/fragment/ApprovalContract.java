package com.incon.service.ui.status.fragment;

import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 12/6/2017.
 */

public interface ApprovalContract {
    interface View extends BaseView {
        void loadingApprovalServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponses);
    }

    interface Presenter {
        void fetchApprovalServiceRequests(int servicerCenterId,int userId);
    }
}
