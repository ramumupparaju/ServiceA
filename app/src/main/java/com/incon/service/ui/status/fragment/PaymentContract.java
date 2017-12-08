package com.incon.service.ui.status.fragment;

import com.incon.service.apimodel.components.productinforesponse.ProductInfoResponse;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 12/6/2017.
 */

public interface PaymentContract {
    interface View extends BaseView {
        void loadReturnHistory(List<ProductInfoResponse> returnHistoryResponseList);
    }

    interface Presenter {
        void returnHistory(int userId);
    }
}
