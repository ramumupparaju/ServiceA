package com.incon.service.callbacks;

import com.incon.service.dto.updatestatus.UpDateStatus;

/**
 * Created by MY HOME on 28-Dec-17.
 */

public interface AssignOptionCallback extends AlertDialogCallback {
    void getUsersListFromServiceCenterId(int serviceCenterId);

    void doUpDateStatusApi(UpDateStatus upDateStatus);

}
