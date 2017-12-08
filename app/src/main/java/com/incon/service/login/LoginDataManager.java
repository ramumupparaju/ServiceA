package com.incon.service.login;


import com.incon.service.apimodel.components.login.LoginResponse;

public interface LoginDataManager {

    void saveLoginDataToPrefs(LoginResponse loginResponse);
}
