package com.incon.service.utils;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;


import com.incon.service.AppConstants;
import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.custom.exception.NoConnectivityException;

import org.json.JSONObject;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ErrorMsgUtil {
    private static final String TAG = ErrorMsgUtil.class.getSimpleName();

    public static Pair<Integer, String> getErrorDetails(Throwable e) {
        Context appContext = ConnectApplication.getAppContext();

        int errorCode =
                Log.d(TAG, "observer ==> onError() obj = " + e);
        String errMsg = null;
        if (e instanceof NetworkErrorException) {
            errMsg = appContext.getString(R.string.error_network);
            errorCode = AppConstants.ErrorCodes.NETWORK_ERROR;
        } else if (e instanceof SocketException || e instanceof SocketTimeoutException) {
            errMsg = appContext.getString(R.string.error_socket);
            errorCode = AppConstants.ErrorCodes.TIMEOUT;
        } else if (e instanceof NoConnectivityException) {
            errMsg = appContext.getString(R.string.error_no_connectivity);
            errorCode = AppConstants.ErrorCodes.NO_NETWORK;
        } else if (e instanceof HttpException) {
            HttpException e1 = (HttpException) e;
            ResponseBody responseBody = e1.response().errorBody();
            errorCode = e1.response().code();
            Logger.e(TAG, "onError = body : " + e1.response().body()
                    + " message " + e1.response().message()
                    + " code " + e1.response().code()
                    + " errorBody " + responseBody);
            try {
                JSONObject errorResponseBody = new JSONObject(responseBody.string());
                Logger.e(TAG, "errorResponseBody = : " + errorResponseBody);
                errMsg = errorResponseBody.has("errorInfo")
                        ? errorResponseBody.getString("errorInfo")
                        : "";
            } catch (Exception ex) {
                errMsg = e1.getMessage();
            }
        } else {
            errMsg = e.getMessage();
        }


        if (TextUtils.isEmpty(errMsg)) {
            errMsg = e.getMessage();
        }
        return new Pair<>(errorCode, errMsg);



    }

}
