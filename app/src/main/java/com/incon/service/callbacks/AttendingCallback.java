package com.incon.service.callbacks;

import com.incon.service.dto.updatestatus.UpDateStatus;

/**
 * Created by PC on 1/9/2018.
 */

public interface AttendingCallback extends AlertDialogCallback {
    void doUpDateStatusApi(UpDateStatus upDateStatus);
    void enteredText(String comment);
}
