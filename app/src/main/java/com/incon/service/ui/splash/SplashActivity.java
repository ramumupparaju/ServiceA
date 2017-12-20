package com.incon.service.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.incon.service.R;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.utils.SharedPrefsUtils;

import net.hockeyapp.android.LoginActivity;

import static com.incon.service.AppConstants.LoginPrefs.LOGGED_IN;


public class SplashActivity extends BaseActivity implements SplashContract.View {

    private static final int SPLASH_DELAY = 2000;
    private static final String TAG = SplashActivity.class.getName();
    private Handler handler;
    private Runnable splashRunnable;

    @Override
    protected int getLayoutId() {
        return (R.layout.activity_splash);
    }

    @Override
    protected void initializePresenter() {
        SplashPresenter splashPresenter = new SplashPresenter();
        splashPresenter.setView(this);
        setBasePresenter(splashPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        setContentView(getLayoutId());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void navigateToMainScreen() {
        splashTimeout();
    }

    private void splashTimeout() {
        handler = new Handler();
        splashRunnable = new Runnable() {
            @Override
            public void run() {

                boolean isLoggedIn = SharedPrefsUtils.loginProvider().
                        getBooleanPreference(LOGGED_IN, false);
                Intent intent;
                if (isLoggedIn) {
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, com.incon.service.ui.login.LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(splashRunnable, SPLASH_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(splashRunnable);
    }
}
