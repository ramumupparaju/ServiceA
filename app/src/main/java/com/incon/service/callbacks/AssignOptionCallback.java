package com.incon.service.callbacks;

import com.incon.service.dto.updatestatus.UpDateStatus;

/**
 * Created by MY HOME on 28-Dec-17.
 */

public interface AssignOptionCallback extends AlertDialogCallback {
    void doUpDateStatusApi(UpDateStatus upDateStatus);
}
