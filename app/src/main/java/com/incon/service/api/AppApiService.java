package com.incon.service.api;


import com.incon.service.AppConstants;
import com.incon.service.BuildConfig;
import com.incon.service.apimodel.base.ApiBaseResponse;
import com.incon.service.apimodel.components.adddesignationresponse.DesignationResponse;
import com.incon.service.apimodel.components.defaults.DefaultsResponse;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.apimodel.components.fetchdesignationsresponse.FetchDesignationsResponse;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.apimodel.components.productinforesponse.ProductInfoResponse;
import com.incon.service.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.service.apimodel.components.registration.SendOtpResponse;
import com.incon.service.apimodel.components.search.ModelSearchResponse;
import com.incon.service.apimodel.components.servicecenterresponse.ServiceCenterResponse;
import com.incon.service.apimodel.components.userslistofservicecenters.UsersListOfServiceCenters;
import com.incon.service.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.service.custom.exception.NoConnectivityException;
import com.incon.service.dto.adddesignation.AddDesignation;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.asignqrcode.AssignQrCode;
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
    public Observable<DesignationResponse> addDesignation(
            int userId, AddDesignation addDesignation) {
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

    //assign qr code to product api
    public Observable<Object> assignQrCodeToProduct(AssignQrCode qrCode) {
        return addNetworkCheck(serviceInstance.assignQrCodeToProduct(qrCode));
    }

    // check qr Codestatus  api
    public Observable<Object> checkQrCodestatus(String qrCode) {
        return addNetworkCheck(serviceInstance.checkQrCodestatus(qrCode));
    }

    // get users list of service centers api
    public Observable<Object> getDesignationsListUsingServiceCenter(int serviceCenterId, int userId) {
        return addNetworkCheck(serviceInstance.getDesignationsListUsingServiceCenter(serviceCenterId, userId));
    }

    // get users list of service centers api
    public Observable<List<UsersListOfServiceCenters>> getUsersListOfServiceCenterApi(int userId) {
        return addNetworkCheck(serviceInstance.getUsersListOfServiceCentersApi(userId));
    }

    // get service centers api  api
    public Observable<List<ServiceCenterResponse>> getServiceCentersApi(int userId) {
        return addNetworkCheck(serviceInstance.getServiceCentersApi(userId));
    }


    //interested history  api
    public Observable<List<ProductInfoResponse>> interestApi(int userId) {
        return addNetworkCheck(serviceInstance.interestApi(userId));
    }

    //return history  api
    public Observable<List<ProductInfoResponse>> returnApi(int userId) {
        return addNetworkCheck(serviceInstance.returnApi(userId));
    }


    // getting user details from qr code
    public Observable<UserInfoResponse> userInfoUsingQrCode(String qrCode) {
        return addNetworkCheck(serviceInstance.userInfoUsingQrCode(qrCode));
    }


    // new user registation  api
    public Observable<UserInfoResponse> newUserRegistation(String phoneNumber) {
        return addNetworkCheck(serviceInstance.newUserRegistation(phoneNumber));
    }

    // getting product details from qr code
    public Observable<ProductInfoResponse> productInfoUsingQrCode(HashMap<String, String> qrCode) {
        return addNetworkCheck(serviceInstance.productInfoUsingQrCode(qrCode));
    }

    //search modelNumber  api
    public Observable<List<ModelSearchResponse>> modelNumberSearch(String modelNumber) {
        return addNetworkCheck(serviceInstance.modelNumberSearch(modelNumber));
    }

    //FetchCategories api
    public Observable<List<FetchCategories>> getCategories(int userId) {
        return addNetworkCheck(serviceInstance.getCategories(userId));
    }


    //warranty registration validate otp api
    public Observable<ValidateWarrantyOtpResponse> validateWarrantyOtp(HashMap<String, String>
                                                                               verify) {
        return addNetworkCheck(serviceInstance.validateWarrantyOtp(verify));
    }

    //warranty registration request otp api
    public Observable<Object> warrantyRequestOtp(String phoneNumber) {
        return addNetworkCheck(serviceInstance.warrantyRequestOtp(phoneNumber));
    }

    /* public Observable<Object> assignQrCodeToProduct(AssignQrCode qrCode) {
        return addNetworkCheck(serviceInstance.assignQrCodeToProduct(qrCode));
    }*/
    // push token  api
    public Observable<Object> pushTokenApi(int userId, PushRegistrarBody pushRegistrarBody) {
        return addNetworkCheck(serviceInstance.pushTokenApi(userId, pushRegistrarBody));
    }
}
