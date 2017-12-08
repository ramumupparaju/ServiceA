package com.incon.service.callbacks;

public interface TextAlertDialogCallback extends AlertDialogCallback {
    byte RESEND_OTP = 2;

    void enteredText(String otpString);

}
