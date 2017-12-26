package com.incon.service.ui.status.fragment;

import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 12/6/2017.
 */

public interface PaymentContract {
    interface View extends BaseView {
        void fetchNewServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponses);
    }

    interface Presenter {
        void fetchNewServiceRequests(int userId);
    }
}
