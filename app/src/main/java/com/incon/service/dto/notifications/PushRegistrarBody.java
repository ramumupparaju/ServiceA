package com.incon.service.dto.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 29 Aug 2017 11:48 AM.
 *
 */
public class PushRegistrarBody {

    @SerializedName("uid")
    @Expose
    private String uId;
    @SerializedName("fcmToken")
    @Expose
    private String pushKey;
    @SerializedName("os_version")
    @Expose
    private String osVersion;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("device_type")
    @Expose
    private String deviceType;


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getPushKey() {
        return pushKey;
    }

    public void setPushKey(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
