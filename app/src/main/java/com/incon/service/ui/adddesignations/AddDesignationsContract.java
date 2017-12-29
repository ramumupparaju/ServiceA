package com.incon.service.ui.adddesignations;

import com.incon.service.apimodel.components.adddesignation.DesignationData;
import com.incon.service.ui.BaseView;

/**
 * Created by PC on 12/19/2017.
 */

public interface AddDesignationsContract {
    interface View extends BaseView {

    }
    interface Presenter {
        void addDesignations(int userId, DesignationData addDesignation);
    }
}
