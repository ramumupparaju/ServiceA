package com.incon.service.ui.addservicecenter;

import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public interface AddServiceCenterContract {
    interface View extends BaseView {
        void loadCategoriesList(List<FetchCategories> categoriesList);

    }
    interface Presenter {
        void getCategories(int merchantId);
        void addingserviceCenter(int userId, AddServiceCenter addServiceCenter);
        }
}
