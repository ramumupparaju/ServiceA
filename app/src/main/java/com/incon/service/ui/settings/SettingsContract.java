package com.incon.service.ui.settings;


import com.incon.service.ui.BaseView;

/**
 * Created on 31 May 2017 11:18 AM.
 *
 */
public interface SettingsContract {

    interface View extends BaseView {
        void loadDefaultsData();
    }

    interface Presenter {
        void getDefaultsApi();
    }

}
