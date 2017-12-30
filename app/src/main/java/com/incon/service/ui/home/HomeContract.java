package com.incon.service.ui.home;


import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created on 31 May 2017 11:18 AM.
 */
public interface HomeContract {

    interface View extends BaseView {
        void serviceCentersSuccessfully();
    }

    interface Presenter {
        void getDefaultStatusData();

        void getServiceCenters(int userId);
    }

}
