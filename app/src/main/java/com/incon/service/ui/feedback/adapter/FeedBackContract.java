package com.incon.service.ui.feedback.adapter;

import com.incon.service.apimodel.components.productinforesponse.ProductInfoResponse;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by shiva on 12/8/2017.
 */

public interface FeedBackContract {

    interface View extends BaseView {
        void loadReturnHistory(List<ProductInfoResponse> returnHistoryResponseList);
    }

    interface Presenter {
        void returnHistory(int userId);
    }
}
