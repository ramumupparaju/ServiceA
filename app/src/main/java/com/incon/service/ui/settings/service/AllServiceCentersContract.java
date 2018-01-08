package com.incon.service.ui.settings.service;

import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 12/19/2017.
 */

public interface AllServiceCentersContract {
    interface View extends BaseView {
        void loadServiceCentersList(List<AddServiceCenter> serviceCenterResponseList);


    }
    interface Presenter {
        void serviceCentersList(int userId);
    }
}
