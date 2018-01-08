package com.incon.service.ui.status.fragment;

import com.incon.service.ui.BaseView;

/**
 * Created by PC on 12/6/2017.
 */

public interface PaymentContract {
    interface View extends BaseView {
        void fetchPaymentServiceRequests(Object o);
    }

    interface Presenter {
        void fetchPaymentServiceRequests(int userId);
    }
}
