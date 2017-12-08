package com.incon.service.ui.notifications;


import com.incon.service.ui.BaseView;

/**
 * Created on 31 May 2017 11:18 AM.
 */
public interface PushContract {

    interface View extends BaseView {
    }

    interface Presenter {
        void pushRegisterApi();
    }

}
