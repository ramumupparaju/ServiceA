package com.incon.service.ui.addservicecenter;

import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.ui.BaseView;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public interface AddServiceCenterContract {
    interface View extends BaseView {

        void loadedDefaultsData(boolean isDataAvailable);
        void serviceCenterAddedSuccessfully();

    }

    interface Presenter {
        void defaultsApi();

        void addingServiceCenter(int userId, AddServiceCenter addServiceCenter);
    }
}
