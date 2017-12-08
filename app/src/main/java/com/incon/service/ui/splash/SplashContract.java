package com.incon.service.ui.splash;


import com.incon.service.ui.BaseView;

public interface SplashContract {

    interface View extends BaseView {
        void navigateToMainScreen();
    }

    interface Presenter {
        // Empty since presenter not required for splash screen
    }
}
