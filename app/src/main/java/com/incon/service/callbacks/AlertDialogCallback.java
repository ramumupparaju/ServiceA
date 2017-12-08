package com.incon.service.callbacks;

public interface AlertDialogCallback {
    byte OK = 0;
    byte CANCEL = 1;

    /**
     * Callback is used to identify which button is tapped in alert dialog box,
     * whether it is OK or CANCEL.
     *
     * @param dialogStatus button that is clicked
     */
    void alertDialogCallback(byte dialogStatus);
}
