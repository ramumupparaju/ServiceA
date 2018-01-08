package com.incon.service.ui.adddesignations;

import com.incon.service.apimodel.components.adddesignation.DesignationData;
import com.incon.service.ui.BaseView;

/**
 * Created by PC on 12/19/2017.
 */

public interface AddDesignationsContract {
    interface View extends BaseView {

        void addDesinationSuccessfully();

        void desinationDeleteSuccessfully();
    }

    interface Presenter {
        void addDesignations(int userId, DesignationData addDesignation);

        void deleteDesignation(int designationId);
    }
}
