package com.incon.service;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import net.hockeyapp.android.CrashManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ConnectApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        // Added to fix : FileUriExposedException in version7.0 while trying to read file from
        // internal storage
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        if (BuildConfig.FLAVOR.equals("client_staging")) {
            CrashManager.register(this);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static ConnectApplication getAppContext() {
        return (ConnectApplication) context;
    }


}
