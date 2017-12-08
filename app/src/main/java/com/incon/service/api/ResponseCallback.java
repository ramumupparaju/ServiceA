package com.incon.service.api;

/**
 * ResponseCallback is a bridge between Presenter and DataManager. Where DataManager interacts
 * with the presenter after a network call processing
 */
public interface ResponseCallback {
    void onSuccess(Object responseData);
    void onFail(String errorMessage);
}
