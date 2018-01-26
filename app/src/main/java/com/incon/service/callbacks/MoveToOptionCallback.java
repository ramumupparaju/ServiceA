package com.incon.service.callbacks;

import com.incon.service.dto.updatestatus.UpDateStatus;

/**
 * Created by MY HOME on 12-Jan-18.
 */

public interface MoveToOptionCallback extends AlertDialogCallback  {
    void doUpDateStatusApi(UpDateStatus upDateStatus);
}
