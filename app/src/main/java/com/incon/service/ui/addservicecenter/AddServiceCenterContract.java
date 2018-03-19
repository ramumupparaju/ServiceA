package com.incon.service.ui.addservicecenter;

import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.apimodel.components.updateservicecenter.UpDateServiceCenterResponse;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.dto.updateservicecenter.UpDateServiceCenter;
import com.incon.service.ui.BaseView;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public interface AddServiceCenterContract {
    interface View extends BaseView {

        void loadedDefaultsData(boolean isDataAvailable);
        void serviceCenterAddedSuccessfully();
        void serviceCenterDeleteSuccessfully();
        void loadUpDateServiceCenterResponce(UpDateServiceCenterResponse upDateServiceCenterResponse);

    }

    interface Presenter {
        void defaultsApi();
        void addingServiceCenter(int userId, AddServiceCenter addServiceCenter);
        void upDateServiceCenter(int serviceCenterId, UpDateServiceCenter upDateServiceCenter);
        void deleteServiceCenter(int serviceCenterId);
    }
}
