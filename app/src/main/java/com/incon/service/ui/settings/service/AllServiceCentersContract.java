package com.incon.service.ui.settings.service;

import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 12/19/2017.
 */

public interface AllServiceCentersContract {
    interface View extends BaseView {
        void loadServiceCentersList(List<ServiceCenterResponse> serviceCenterResponseList);


    }
    interface Presenter {
        void serviceCentersList(int userId);
    }
}
