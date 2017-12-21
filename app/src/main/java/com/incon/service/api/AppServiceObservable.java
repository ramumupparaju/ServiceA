package com.incon.service.api;


import com.incon.service.apimodel.base.ApiBaseResponse;
import com.incon.service.apimodel.components.adddesignationresponse.DesignationResponse;
import com.incon.service.apimodel.components.defaults.DefaultsResponse;
import com.incon.service.apimodel.components.favorites.AddUserAddressResponse;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;
import com.incon.service.apimodel.components.fetchdesignationsresponse.FetchDesignationsResponse;
import com.incon.service.apimodel.components.login.LoginResponse;
import com.incon.service.apimodel.components.productinforesponse.ProductInfoResponse;
import com.incon.service.apimodel.components.qrcodebaruser.UserInfoResponse;
import com.incon.service.apimodel.components.registration.SendOtpResponse;
import com.incon.service.apimodel.components.search.ModelSearchResponse;
import com.incon.service.apimodel.components.validateotp.ValidateWarrantyOtpResponse;
import com.incon.service.dto.adddesignation.AddDesignation;
import com.incon.service.dto.addfavorites.AddUserAddress;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.asignqrcode.AssignQrCode;
import com.incon.service.dto.login.LoginUserData;
import com.incon.service.dto.notifications.PushRegistrarBody;
import com.incon.service.dto.registration.Registration;
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
    Observable<List<FetchCategories>> defaultsApi();

    //login api
    @POST("service/login")
    Observable<LoginResponse> login(@Body LoginUserData loginUserData);

    //registration api
    @POST("service/register")
    Observable<LoginResponse> register(@Body Registration registrationBody);

    // add service center  api
    @POST("service/addcenter/{userId}")
    Observable<LoginResponse> addServiceCenter(@Path(
            "userId") int userId, @Body AddServiceCenter addServiceCenter);

    // add user api
    @POST("service/adduser/{userId}")
    Observable<LoginResponse> addUser(@Path("userId") int userId, @Body AddUser addUser);


    // add adddesignation api
    @POST("service/adddesignation/{userId}")
    Observable<DesignationResponse> addDesignation(@Path("userId") int userId,
                                                   @Body AddDesignation addDesignation);

    //fetch designation api
    @GET("service/getdesignations/{serviceCenterId}/{userId}")
    Observable<List<FetchDesignationsResponse>> fetchDesignations(@Path("serviceCenterId") int
                                                                          serviceCenterId
            ,@Path("userId") int userId);

    // store id  api
    @Multipart
    @POST("merchant/logoupdate/{storeId}")
    Observable<Object> uploadStoreLogo(@Path("storeId") String storeId,
                                       @Part MultipartBody.Part storeLogo);

    //registration request otp
    @GET("user/requestotp/{phoneNumber}/register")
    Observable<Object> registerRequestOtp(@Path("phoneNumber") String phoneNumber);

    //registration request password otp
    @GET("user/requestotp/{phoneNumber}/password")
    Observable<Object> registerRequestPasswordOtp(@Path("phoneNumber") String phoneNumber);

    // user profile update api
    @POST("user/updateuser/{userId}")
    Observable<LoginResponse> upDateUserProfile(@Path(
            "userId") int userId, @Body UpDateUserProfile upDateUserProfile);

    @POST("account/sendOtp")
    Observable<SendOtpResponse> sendOtp(@Body HashMap<String, String> email);

    @POST("user/validateotp")
    Observable<LoginResponse> validateOtp(@Body HashMap<String, String> verify);

    @POST("merchant/forgotpassword")
    Observable<ApiBaseResponse> forgotPassword(@Body HashMap<String, String> phoneNumber);

    // change password api
    @POST("merchant/changepassword")
    Observable<LoginResponse> changePassword(@Body HashMap<String, String> password);

    // check qr Codestatus  api
    @GET("product/checkqropnestatus/{qrCode}")
    Observable<Object> checkQrCodestatus(@Path("qrCode") String qrCode);

    // purchased history  api
    @GET("user/history/purchased/{userId}")
    Observable<List<ProductInfoResponse>> purchasedApi(@Path("userId") int userId);

    // add favourites  api
    @POST("user/addtofavourites")
    Observable<Object> addToFavotites(@Body HashMap<String, String> favoriteMap);

    // add favourites  api
    @GET("user/history/deletepurchased/{warrantyId}")
    Observable<Object> deleteProduct(@Path("warrantyId") int warrantyId);

    //assign qr code to product api
    @POST("product/assign")
    Observable<Object> assignQrCodeToProduct(@Body AssignQrCode qrCode);

    //interested history  api
    @GET("user/history/interested/{userId}")
    Observable<List<ProductInfoResponse>> interestApi(@Path("userId") int userId);

    //delete interest product api
    @GET("user/history/deleteinterested/{interestId}")
    Observable<Object> deleteApi(@Path("interestId") int interestId);

    //buy requests api
    @POST("user/buyrequest")
    Observable<Object> buyRequestApi(@Body HashMap<String, String> buyRequestBody);

    //return history  api
    @GET("user/history/return/{userId}")
    Observable<List<ProductInfoResponse>> returnApi(@Path("userId") int userId);

    // getting user addresses api
    @GET("user/getaddresses/{userId}")
    Observable<List<AddUserAddressResponse>> getAddressesApi(@Path("userId") int userId);

    //  user addresses api
    @POST("user/addaddress")
    Observable<Object> addProductAddress(@Body AddUserAddress addUserAddress);

    //  favourites addresse api
    @GET("user/favourites/{userId}/{addressId}")
    Observable<List<ProductInfoResponse>> favouritesProductApi(
            @Path("userId") int userId, @Path("addressId") int addressId);

    @GET("user/getuser/scan/{qrCode}/")
    Observable<UserInfoResponse> userInfoUsingQrCode(@Path("qrCode") String qrCode);

    // user intereste api
    @POST("user/interested/{customerId}")
    Observable<ProductInfoResponse> userInterestedUsingQrCode(@Path("customerId") int customerId, @Body
            HashMap<String,
                    String> qrCode);

    @POST("product/getproduct")
    Observable<ProductInfoResponse> productInfoUsingQrCode(@Body HashMap<String, String> qrCode);

    //search modelNumber  api
    @GET("product/search/{modelNumber}")
    Observable<List<ModelSearchResponse>> modelNumberSearch(@Path("modelNumber")
                                                                    String modelNumber);

    //FetchCategories api
    @GET("merchant/getcategories/{merchantId}")
    Observable<List<FetchCategories>> getCategories(@Path("merchantId") int merchantId);


    //warranty registration validateotp api
    @POST("warranty/validateotp")
    Observable<ValidateWarrantyOtpResponse> validateWarrantyOtp(@Body HashMap<String, String>
                                                                        verify);

    //warranty registration requestotp api
    @GET("warranty/requestotp/{phoneNumber}/password")
    Observable<Object> warrantyRequestOtp(@Path("phoneNumber") String phoneNumber);

    //transfer product api
    @GET("user/transfer/{phoneNumber}/{userId}")
    Observable<Object> transferRequest(@Path("phoneNumber") String phoneNumber,
                                       @Path("userId") int userId);

    // new user registation  api
    @POST("user/newuser/{phoneNumber}")
    Observable<UserInfoResponse> newUserRegistation(@Path("phoneNumber")
                                                            String phoneNumber);

    // push token  api
    @POST("user/updatefcmtoken/{userId}")
    Observable<Object> pushTokenApi(@Path("userId") int userId, @Body PushRegistrarBody
            pushRegistrarBody);


}
