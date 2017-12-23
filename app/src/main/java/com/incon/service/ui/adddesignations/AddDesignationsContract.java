package com.incon.service.ui.adddesignations;

import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.apimodel.components.servicecenterresponse.ServiceCenterResponse;
import com.incon.service.dto.adddesignation.AddDesignation;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by PC on 12/19/2017.
 */

public interface AddDesignationsContract {
    interface View extends BaseView {
        void loadServiceCentersList(List<ServiceCenterResponse> serviceCenterResponseList);


    }
    interface Presenter {
        void addDesignations(int userId, AddDesignation addDesignation);
        void serviceCentersList(int userId);

    }
}
