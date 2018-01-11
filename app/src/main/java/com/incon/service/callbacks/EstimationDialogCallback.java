package com.incon.service.callbacks;

import com.incon.service.dto.updatestatus.UpDateStatus;

/**
 * Created by MY HOME on 11-Jan-18.
 */

public interface EstimationDialogCallback extends AlertDialogCallback{
    void dateClicked(String date);
    void doUpDateStatusApi(UpDateStatus upDateStatus);
}
