package com.incon.service;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.apimodel.components.getstatuslist.DefaultStatusData;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;

import net.hockeyapp.android.CrashManager;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ConnectApplication extends Application {

    private List<FetchCategories> fetchCategoriesList;
    private List<DefaultStatusData> defaultStausData;
    private List<ServiceCenterResponse> serviceCenterList;
    private static Context context;

    public List<ServiceCenterResponse> getServiceCenterList() {
        return serviceCenterList;
    }

    public void setServiceCenterList(List<ServiceCenterResponse> serviceCenterList) {
        this.serviceCenterList = serviceCenterList;
    }

    public List<FetchCategories> getFetchCategoriesList() {
        return fetchCategoriesList;
    }

    public void setFetchCategoriesList(List<FetchCategories> fetchCategoriesList) {
        this.fetchCategoriesList = fetchCategoriesList;
    }

    public List<DefaultStatusData> getDefaultStausData() {
        return defaultStausData;
    }

    public void setDefaultStausData(List<DefaultStatusData> defaultStausData) {
        this.defaultStausData = defaultStausData;
    }

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
