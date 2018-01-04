package com.incon.service.api;


import com.incon.service.AppConstants;
import com.incon.service.BuildConfig;
import com.incon.service.apimodel.base.ApiBaseResponse;
import com.incon.service.apimodel.components.adddesignation.DesignationData;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.getstatuslist.DefaultStatusData;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.apimodel.components.registration.SendOtpResponse;
import com.incon.service.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.service.custom.exception.NoConnectivityException;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.login.LoginUserData;
import com.incon.service.dto.notifications.PushRegistrarBody;
import com.incon.service.dto.registration.Registration;
import com.incon.service.dto.update.UpDateUserProfile;
import com.incon.service.utils.NetworkUtil;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;

public class AppApiService implements AppConstants {

    private static AppApiService apiDataService;
    private AppServiceObservable serviceInstance;

    private AppApiService() {
        ServiceGenerator serviceGenerator = new ServiceGenerator(BuildConfig.SERVICE_ENDPOINT);
        serviceInstance = serviceGenerator.getConnectService();
    }

    public static AppApiService getInstance() {
        if (apiDataService == null) {
            apiDataService = new AppApiService();
        }
        return apiDataService;
    }

    private <T> Observable<T> checkNetwork() {
        //Returns NetworkStatusException when there is no network
        //Otherwise return nothing
        if (NetworkUtil.isOnline()) {
            return Observable.never();
        } else {
            return Observable.error(new NoConnectivityException());
        }
    }

    private <T> Observable<T> addNetworkCheck(Observable<T> observable) {
        Observable<T> network = checkNetwork();
        return network
                .ambWith(observable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //default data api
    public Observable<List<FetchCategories>> defaultsApi() {
        return addNetworkCheck(serviceInstance.defaultsApi());
    }

    //login api
    public Observable<LoginResponse> login(LoginUserData loginUserData) {
        return addNetworkCheck(serviceInstance
                .login(loginUserData));
    }

    // change password api
    public Observable<LoginResponse> changePassword(HashMap<String, String> password) {
        return addNetworkCheck(serviceInstance.changePassword(password));
    }

    // forgot password api
    public Observable<ApiBaseResponse> forgotPassword(HashMap<String, String> email) {
        return addNetworkCheck(serviceInstance.forgotPassword(email));
    }

    //registration api
    public Observable<LoginResponse> register(Registration registrationBody) {
        return addNetworkCheck(serviceInstance.register(registrationBody));
    }

    // get status list api
    public Observable<List<DefaultStatusData>> getStatusList() {
        return addNetworkCheck(serviceInstance.getStatusList());
    }

    // add service center api
    public Observable<LoginResponse> addServiceCenter(
            int userId, AddServiceCenter addServiceCenter) {
        return addNetworkCheck(serviceInstance.addServiceCenter(userId, addServiceCenter));
    }

    // add user api
    public Observable<LoginResponse> addUser(
            int userId, AddUser addUser) {
        return addNetworkCheck(serviceInstance.addUser(userId, addUser));
    }


    // add add designation api
    public Observable<DesignationData> addDesignation(
            int userId, DesignationData addDesignation) {
        return addNetworkCheck(serviceInstance.addDesignation(userId, addDesignation));
    }


    // service center logo  api
    public Observable<Object> uploadServiceCenterLogo(int serviceCenterId, MultipartBody.Part serviceCenterLogo) {
        return addNetworkCheck(serviceInstance.uploadServiceCenterLogo(String.valueOf(serviceCenterId), serviceCenterLogo));
    }


    // user profile update api
    public Observable<LoginResponse> upDateUserProfile(
            int userId, UpDateUserProfile upDateUserProfile) {
        return addNetworkCheck(serviceInstance.upDateUserProfile(userId, upDateUserProfile));
    }

    //registration request otp
    public Observable<Object> registerRequestOtp(String phoneNumber) {
        return addNetworkCheck(serviceInstance.registerRequestOtp(phoneNumber));
    }

    //registration request password otp
    public Observable<Object> registerRequestPasswordOtp(String phoneNumber) {
        return addNetworkCheck(serviceInstance.registerRequestPasswordOtp(phoneNumber));
    }


    public Observable<SendOtpResponse> sendOtp(HashMap<String, String> email) {
        return addNetworkCheck(serviceInstance.sendOtp(email));
    }

    // validate otp api
    public Observable<LoginResponse> validateOtp(HashMap<String, String> verify) {
        return addNetworkCheck(serviceInstance.validateOtp(verify));
    }

    // get users list of service centers api
    public Observable<List<DesignationData>> getDesignationsListUsingServiceCenter(int userId, int serviceCenterId) {
        return addNetworkCheck(serviceInstance.getDesignationsListUsingServiceCenter(userId, serviceCenterId));
    }

    // get users list of service centers api
    public Observable<List<AddUser>> getUsersListOfServiceCenterApi(int serviceCenterId) {
        return addNetworkCheck(serviceInstance.getUsersListOfServiceCentersApi(serviceCenterId));
    }

    // get service centers api  api
    public Observable<List<AddServiceCenter>> getServiceCentersApi(int userId) {
        return addNetworkCheck(serviceInstance.getServiceCentersApi(userId));
    }

    // delete designation  api
    public Observable<Object> deletedesignationApi(int designationId) {
        return addNetworkCheck(serviceInstance.deletedesignationApi(designationId));
    }

    // delete service center  api
    public Observable<Object> deleteServiceCenterApi(int serviceCenterId) {
        return addNetworkCheck(serviceInstance.deleteServiceCenterApi(serviceCenterId));
    }

    // delete user api
    public Observable<Object> deleteUserApi(int serviceCenterId) {
        return addNetworkCheck(serviceInstance.deleteUserApi(serviceCenterId));
    }

    // fetch  new service request api
    public Observable<List<FetchNewRequestResponse>> fetchNewServiceRequestApi(int servicerCenterId) {
        return addNetworkCheck(serviceInstance.fetchNewServiceRequestApi(servicerCenterId));
    }

    // fetch  approval service request api
    public Observable<Object> fetchApprovalServiceRequestApi(int userId) {
        return addNetworkCheck(serviceInstance.fetchApprovalServiceRequestApi(userId));
    }

    // fetch  repair service request api
    public Observable<Object> fetchRepairServiceRequestApi(int userId) {
        return addNetworkCheck(serviceInstance.fetchRepairServiceRequestApi(userId));
    }   // fetch  repair service request api

    public Observable<Object> fetchPaymentServiceRequestApi(int userId) {
        return addNetworkCheck(serviceInstance.fetchPaymentServiceRequestApi(userId));
    }


    //warranty registration validate otp api
    public Observable<ValidateWarrantyOtpResponse> validateWarrantyOtp(HashMap<String, String>
                                                                               verify) {
        return addNetworkCheck(serviceInstance.validateWarrantyOtp(verify));
    }

    // push token  api
    public Observable<Object> pushTokenApi(int userId, PushRegistrarBody pushRegistrarBody) {
        return addNetworkCheck(serviceInstance.pushTokenApi(userId, pushRegistrarBody));
    }
}
