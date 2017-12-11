package com.incon.service.api;


import com.incon.service.apimodel.base.ApiBaseResponse;
import com.incon.service.apimodel.components.defaults.DefaultsResponse;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.apimodel.components.productinforesponse.ProductInfoResponse;
import com.incon.service.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.service.apimodel.components.registration.SendOtpResponse;
import com.incon.service.apimodel.components.search.ModelSearchResponse;
import com.incon.service.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.service.dto.asignqrcode.AssignQrCode;
import com.incon.service.dto.login.LoginUserData;
import com.incon.service.dto.notifications.PushRegistrarBody;
import com.incon.service.dto.registration.Registration;
import com.incon.service.dto.update.UpDateStoreProfile;
import com.incon.service.dto.update.UpDateUserProfile;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AppServiceObservable {
    //default data api
    @GET("defaults")
    Observable<DefaultsResponse> defaultsApi();

    //login api
    @POST("merchant/login")
    Observable<LoginResponse> login(@Body LoginUserData loginUserData);

    //registration api
    @POST("merchant/register")
    Observable<LoginResponse> register(@Body Registration registrationBody);

    // user profile update api
    @POST("merchant/updatemerchant/{merchantId}")
    Observable<LoginResponse> upDateUserProfile(@Path(
            "merchantId") int merchantId, @Body UpDateUserProfile upDateUserProfile);

    // store profile update api
    @POST("merchant/updatestoredetails/{merchantId}")
    Observable<LoginResponse> upDateStoreProfile(@Path(
            "merchantId") int merchantId, @Body UpDateStoreProfile upDateUserProfile);

    //registration request otp
    @GET("user/requestotp/{phoneNumber}/register")
    Observable<Object> registerRequestOtp(@Path("phoneNumber") String phoneNumber);

    //registration request password otp
    @GET("user/requestotp/{phoneNumber}/password")
    Observable<Object> registerRequestPasswordOtp(@Path("phoneNumber") String phoneNumber);

    // store id  api
    @Multipart
    @POST("merchant/logoupdate/{storeId}")
    Observable<Object> uploadStoreLogo(@Path("storeId") String storeId,
                                       @Part MultipartBody.Part storeLogo);

    // send otp api
    @POST("account/sendOtp")
    Observable<SendOtpResponse> sendOtp(@Body HashMap<String, String> email);

    // validate otp api
    @POST("user/validateotp")
    Observable<LoginResponse> validateOtp(@Body HashMap<String, String> verify);

    // forgot password  api
    @POST("merchant/forgotpassword")
    Observable<ApiBaseResponse> forgotPassword(@Body HashMap<String, String> phoneNumber);

    // change password  api
    @POST("merchant/changepassword")
    Observable<LoginResponse> changePassword(@Body HashMap<String, String> password);

    // check qr Codestatus  api
    @GET("product/checkqropnestatus/{qrCode}")
    Observable<Object> checkQrCodestatus(@Path("qrCode") String qrCode);

    //assign qr code to product api
    @POST("product/assign")
    Observable<Object> assignQrCodeToProduct(@Body AssignQrCode qrCode);

    // purchased history  api
    @GET("merchant/history/purchased/{userId}")
    Observable<List<ProductInfoResponse>> purchasedApi(@Path("userId") int userId);

    //return product api
    @GET("merchant/return/{purchasedId}/{userId}")
    Observable<Object> returnProductApi(
            @Path("userId") int userId, @Path("purchasedId") int purchasedId);

    //interested history  api
    @GET("merchant/history/interested/{userId}")
    Observable<List<ProductInfoResponse>> interestApi(@Path("userId") int userId);

    //return history  api
    @GET("merchant/history/return/{userId}")
    Observable<List<ProductInfoResponse>> returnApi(@Path("userId") int userId);

    //buy requests api
    @GET("merchant/buy-requests/{userId}")
    Observable<List<ProductInfoResponse>> buyRequestApi(@Path("userId") int userId);

    //update buy requests api
    @POST("merchant/buyrequest/update/{requestId}")
    Observable<ProductInfoResponse> updateBuyRequestApi(@Path("requestId") int requestId,
                                                        @Body HashMap<String, String> requestParams);


    // getting user details from phone number
    @GET("user/warranty/getuser/{phoneNumber}")
    Observable<UserInfoResponse> userInfoUsingPhoneNumber(@Path("phoneNumber") String phoneNumber);

    // getting user details from qr code
    @GET("user/getuser/scan/{qrCode}/")
    Observable<UserInfoResponse> userInfoUsingQrCode(@Path("qrCode") String qrCode);

    // getting product details from qr code
    @POST("product/getproduct")
    Observable<ProductInfoResponse> productInfoUsingQrCode(@Body HashMap<String, String> qrCode);

    //search model number  api
    @GET("product/search/{modelNumber}")
    Observable<List<ModelSearchResponse>> modelNumberSearch(@Path("modelNumber")
                                                                    String modelNumber);

    //fetch categories api
    @GET("merchant/getcategories/{merchantId}")
    Observable<List<FetchCategories>> getCategories(@Path("merchantId") int merchantId);


    //warranty registration validateotp api
    @POST("warranty/validateotp")
    Observable<ValidateWarrantyOtpResponse> validateWarrantyOtp(@Body HashMap<String, String>
                                                                        verify);

    //warranty registration requestotp api
    @GET("warranty/requestotp/{phoneNumber}/password")
    Observable<Object> warrantyRequestOtp(@Path("phoneNumber") String phoneNumber);

    // new user registation  api
    @POST("user/newuser/{phoneNumber}")
    Observable<UserInfoResponse> newUserRegistation(@Path("phoneNumber")
                                                            String phoneNumber);

    // registation  api
    @POST("registerPush")
    Observable<Object> pushTokenApi(@Body PushRegistrarBody pushRegistrarBody);


}
