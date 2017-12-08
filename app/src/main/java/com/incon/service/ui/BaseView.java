package com.incon.service.ui;

import android.util.Pair;

public interface BaseView {

    // Try to add common view updates, like showProgress and hideProgress

    void showProgress(String message);

    void hideProgress();

    void showErrorMessage(String errorMessage);

    void handleException(Pair<Integer, String> error);

}
