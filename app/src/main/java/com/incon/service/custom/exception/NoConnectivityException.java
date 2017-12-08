package com.incon.service.custom.exception;


import com.incon.service.ConnectApplication;
import com.incon.service.R;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return ConnectApplication.getAppContext().getString(R.string.msg_check_internet);
    }
}
