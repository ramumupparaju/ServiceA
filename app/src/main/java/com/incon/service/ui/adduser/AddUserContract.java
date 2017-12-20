package com.incon.service.ui.adduser;

import com.incon.service.apimodel.components.fetchdesignationsresponse.FetchDesignationsResponse;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BaseView;

import java.util.List;

/**
 * Created by MY HOME on 17-Dec-17.
 */

public interface AddUserContract {
    interface View extends BaseView {
        void loadFetchDesignations(List<FetchDesignationsResponse> fetchDesignationsResponse);
    }

    interface Presenter {
        void addingUser(int userId, AddUser addUser);
        void fetchDesignations(int serviceCenterId, int userId);
    }
}
