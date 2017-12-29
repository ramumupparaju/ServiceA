package com.incon.service.login;


import com.incon.service.AppConstants;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.utils.SharedPrefsUtils;

public class LoginDataManagerImpl implements LoginDataManager, AppConstants.LoginPrefs {
    private static final String TAG = LoginDataManagerImpl.class.getName();

    @Override
    public void saveLoginDataToPrefs(LoginResponse loginResponse) {
        SharedPrefsUtils sharedPrefsUtils = SharedPrefsUtils.loginProvider();

        sharedPrefsUtils.setBooleanPreference(LOGGED_IN, true);
        //Adding user details to preferences
        sharedPrefsUtils.setIntegerPreference(USER_ID,
                loginResponse.getId());
        sharedPrefsUtils.setStringPreference(USER_NAME,
                loginResponse.getName());
        sharedPrefsUtils.setStringPreference(USER_EMAIL_ID,
                loginResponse.getEmail());
        sharedPrefsUtils.setStringPreference(USER_PHONE_NUMBER,
                loginResponse.getMobileNumber());
        sharedPrefsUtils.setStringPreference(USER_DOB,
                loginResponse.getDobInMillis());
        sharedPrefsUtils.setStringPreference(USER_GENDER,
                loginResponse.getGender());
        sharedPrefsUtils.setStringPreference(USER_ADDRESS,
                loginResponse.getAddress());
        sharedPrefsUtils.setStringPreference(USER_UUID,
                loginResponse.getUuid());

        //storing service center details
        sharedPrefsUtils.setIntegerPreference(SERVICE_CENTER_ID,
                loginResponse.getStore().getId());

    }
}
