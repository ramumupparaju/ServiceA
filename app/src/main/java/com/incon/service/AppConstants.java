package com.incon.service;

import java.util.Locale;

public interface AppConstants {

    String TERMS_CONDITIONS_URL = "https://www.google.co.in"; //TODO have to change
    String WEB_IMAGE = "http";
    String MULTIPART_FORM_DATA = "image/*";
    String COMMA_SEPARATOR = ",";
    int DEFAULT_VALUE = Integer.MIN_VALUE;

    String BUILD_FLAVOR = "moonz_dev";
    int VALIDATION_SUCCESS = 0;
    int VALIDATION_FAILURE = -1;
    int VALIDATION_ZIPCODE_LENGTH = 5;
    String DELIMITOR = "-";
    int DEFAULT_GOOGLE_MAP_ZOOM_LEVEL = 16;
    int DEAULT_VALUE = Integer.MAX_VALUE;


    interface RegistrationConstants {
        String SERVICE_INDIVIDUAL = "I";
        String SERVICE_GROUP = "G";
    }

    interface StatusConstants {
        String BUY_REQUEST_ACCEPT = "A";
        String BUY_REQUEST_REJECT = "R";
        String PENDING = "PEN";
        String DISPATCHES_ON = "DSN";
        String DISPATCHED = "DSD";
        String DELIVERED = "DLD";
        String INSTALLED = "INS";
    }

    interface BooleanConstants {
        int IS_TRUE = 1;
        int IS_FALSE = 0;
    }


    interface FilterConstants {
        String NONE = "none";
        String NAME = "name";
        String BRAND = "brand";
    }

    interface WarrantyRegistrationConstants {
        int MINIMUM_MODELNUMBER_TO_SEARCH = 2;
    }

    interface GoogleMapConstants {
        int GEOCODER_MAX_ADDRESS_RESULTS = 1;
        float DEFAULT_ZOOM_LEVEL = 12.0f;
    }

    interface HttpErrorCodeConstants {
        int ERROR_UNAUTHORIZED = 401;
        int ERROR_FORBIDDEN = 403; // pay load error
    }

    interface UserConstants {
        int SUPER_ADMIN_TYPE = 7;
        int USER_TYPE = 8;
    }

    interface PushConstants {
        String BUNDLE_TEXT = "text";
        String BUNDLE_TITLE = "title";
        String BUNDLE_EXTRAS = "extras";
    }

    interface LoginValidation {
        int PHONE_NUMBER_REQ = 1;
        int PHONE_NUMBER_NOTVALID = 2;
        int PASSWORD_REQ = 3;
    }

    interface AgeConstants {
        int USER_DOB = 16;
    }

    interface RegistrationValidation {
        int NAME_REQ = 1;
        int PHONE_REQ = 3;
        int PHONE_MIN_DIGITS = 10;
        int GENDER_REQ = 17;
        int DOB_REQ = 13;
        int EMAIL_REQ = 5;
        int EMAIL_NOTVALID = 6;
        int PASSWORD_REQ = 7;
        int PASSWORD_PATTERN_REQ = 11;
        int RE_ENTER_PASSWORD_REQ = 18;
        int RE_ENTER_PASSWORD_DOES_NOT_MATCH = 19;
        int DOB_FUTURE_DATE = 14;
        int DOB_PERSON_LIMIT = 15;
        int CATEGORY_REQ = 21;
        int ADDRESS_REQ = 22;
        int GSTN_REQ = 23;
        int STORE_LOGO = 24;
        int DIVISION_REQ = 25;
        int BRAND_REQ = 26;
        int CREATED_DATE_REQ = 27;
        int SERVICE_CENTER_ID = 28;
        int SERVICE_CENTER_ROLE_ID = 29;
        int CREATED_FUTURE_DATE = 30;

        int ZIPCODE_REQ = 9;
        int ZIPCODE_INVALID = 12;
        int TIMEZONE_REQ = 8;
    }


    interface AddUserValidations {
        int NAME_REQ = 1;
        int PHONE_REQ = 3;
        int PHONE_MIN_DIGITS = 10;
        int GENDER_REQ = 17;
        int DOB_REQ = 13;
        int EMAIL_REQ = 5;
        int EMAIL_NOTVALID = 6;
        int PASSWORD_REQ = 7;
        int PASSWORD_PATTERN_REQ = 11;
        int RE_ENTER_PASSWORD_REQ = 18;
        int RE_ENTER_PASSWORD_DOES_NOT_MATCH = 19;
        int DOB_FUTURE_DATE = 14;
        int DOB_PERSON_LIMIT = 15;
        int ADDRESS_REQ = 22;
        int CREATED_DATE_REQ = 27;
        int SERVICE_CENTER_NAME = 28;
        int SERVICE_DISIGNATION = 29;
        int SERVICE_CENTER_DESIGNATION = 30;
        int REPORTING_PERSON = 31;
    }

    interface AddServiceCenterValidation {
        int NAME_REQ = 1;
        int PHONE_REQ = 3;
        int PHONE_MIN_DIGITS = 10;
        int GENDER_REQ = 17;
        int DOB_REQ = 13;
        int EMAIL_REQ = 5;
        int EMAIL_NOTVALID = 6;
        int PASSWORD_REQ = 7;
        int PASSWORD_PATTERN_REQ = 11;
        int RE_ENTER_PASSWORD_REQ = 18;
        int RE_ENTER_PASSWORD_DOES_NOT_MATCH = 19;
        int DOB_FUTURE_DATE = 14;
        int DOB_PERSON_LIMIT = 15;
        int CATEGORY_REQ = 21;
        int ADDRESS_REQ = 22;
        int GSTN_REQ = 23;
        int STORE_LOGO = 24;
        int DIVISION_REQ = 25;
        int BRAND_REQ = 26;
        int CREATED_DATE_REQ = 27;
        int SERVICE_CENTER_ID = 28;
        int SERVICE_CENTER_ROLE_ID = 29;
        int CREATED_FUTURE_DATE = 30;

        int ZIPCODE_REQ = 9;
        int ZIPCODE_INVALID = 12;
        int TIMEZONE_REQ = 8;
    }


    interface AddDesignationsValidation {
        int DESCRIPTION = 1;
        int NAME_REQ = 2;
        int SERVICE_CENTER_NAME = 3;


    }


    interface PasswordValidation {
        int NEWPWD_REQ = 1;
        int NEWPWD_PATTERN_REQ = 11;
        int CONFIRMPWD_REQ = 2;
        int PWD_NOTMATCH = 3;
    }

    interface ChangeEmailValidation {
        int CURRENT_PWD_REQ = 1;
        int CURRENT_PWD_PATTERN_REQ = 2;
        int EMAIL_REQ = 3;
        int EMAIL_NOTVALID = 4;
    }

    interface ChangeCurrentPasswordValidation {
        int CURRENT_PWD_REQ = 1;
        int NEW_PWD_REQ = 2;
        int NEW_PWD_PATTERN_REQ = 3;
        int CONFIRM_PWD_REQ = 4;
        int CONFIRM_PWD_PATTERN_REQ = 5;
        int NEW_CONFIRM_PWD_NOT_MATCHED = 6;
    }

    interface IntentConstants {
        String MODEL_SEARCH_RESPONSE = "modelSearchResponse";

        String SCANNED_TITLE = "scannedTitle";
        String SCANNED_CODE = "scanedCode";
        String USER_PHONE_NUMBER = "userPhoneNumber";
        String IMAGE_PATH = "imagePath";
        String ADDRESS_COMMA = "addressDetails";
        String LOCATION_COMMA = "locationDetails";
        String FROM_FORGOT_PASSWORD_SCREEN = "fromForgotPasswordScreen";
        String ADDRESS_INFO = "addressInfo";
        String SERVICE_CENTER_DATA = "serviceCenterData";
        String SERVICE_CENTER_ID = "service_center_id";
        String USER_DATA = "userData";
        String USER_DATA_LIST = "user_data_list";
        String DESIGNATION_DATA = "designationData";
    }


    interface UpDateUserProfileValidation {
        int NAME_REQ = 1;
        int PHONE_REQ = 3;
        int PHONE_MIN_DIGITS = 10;
        int GENDER_REQ = 17;
        int DOB_REQ = 13;
        int EMAIL_REQ = 5;
        int EMAIL_NOTVALID = 6;
        int PASSWORD_REQ = 7;
        int PASSWORD_PATTERN_REQ = 11;
        int RE_ENTER_PASSWORD_REQ = 18;
        int RE_ENTER_PASSWORD_DOES_NOT_MATCH = 19;
        int DOB_FUTURE_DATE = 14;
        int DOB_PERSON_LIMIT = 15;

        int CATEGORY_REQ = 21;
        int ADDRESS_REQ = 22;
        int GSTN_REQ = 23;
        int STORE_LOGO = 24;

        int ZIPCODE_REQ = 9;
        int ZIPCODE_INVALID = 12;
        int TIMEZONE_REQ = 8;
        int MIN_DAYS = 7;
    }


    interface BundleConstants {
        String QRCODE_DATA = "qrcodeData";
        String SCANNED_QRCODE = "scannedQrcode";
        String WARRANTY_DATA = "warrantyData";
        String ADD_NEW_MODEL_DATA = "addnewmodelData";
        String LOCATION_ADDRESS = "locationAddress";
        String PRODUCT_INFO_RESPONSE = "productInfoResponse";

        String FROM_FAVORITES = "fromFavorites";
        String ADDRESS_ID = "addressId";
    }

    interface PushSubTypeConstants {
        String CONNECT_PUSH = "tueoPush";
        String PUSH_DEVICE_TYPE = "android";
    }


    interface CachePrefs {
        String IS_ACCESS_CODE_VERIFIED = "isAccessCodeVerified";
        String IS_AAP_OFFLINE_IMAGE = "isAapOfflineImage";
        String EXTRACT_ZIP = "extractZip";
        String FILTER_NAME = "filterName";
        String IS_SCAN_FIRST = "isScanFirst";
    }

    interface LoginPrefs {
        //User details
        String USER_ID = "userId";
        String USER_NAME = "userName";
        String USER_EMAIL_ID = "userEmailId";
        String USER_PHONE_NUMBER = "userPhoneNumber";
        String USER_DOB = "userDob";
        String USER_GENDER = "userGender";
        String USER_ADDRESS = "userAddress";
        String USER_TYPE = "userType";
        String USER_IS_ADMIN = "userIsAdmin";
        String USER_CITY = "userCity";
        String USER_STATE = "userState";
        String USER_POSTAL_CODE = "userPostalCode";
        String USER_PASSWORD = "userPassword";
        String USER_CONFIRM_PASSWORD = "userConfirmPassword";
        String USER_UUID = "uuid";
        String SERVICE_CENTER_ID = "serviceCenterId";
        String STORE_LOGO = "storeLogo";


        String IS_REGISTERED = "isRegistered";
        String IS_FORGOT_PASSWORD = "isForgotPassword";
        String LOGGED_IN = "isLoggedIn";
        String PUSH_TOKEN_STATUS = "pushTokenStatus";
        String ACCESS_TOKEN = "accesstoken";
    }

    interface ApiRequestKeyConstants {
        String HEADER_AUTHORIZATION = "Authorization";
        String BODY_EMAIL = "email";
        String BODY_CUSTOMER_ID = "customerId";
        String BODY_MERCHANT_ID = "merchantId";
        String BODY_INTEREST_ID = "interestId";
        String BODY_QRCODE_ID = "qrCodeId";
        String BODY_COMMENTS = "comments";
        String BODY_USER_ID = "userId";
        String BODY_ADDRESS_ID = "addressId";
        String BODY_WARRANTY_ID = "warrantyId";
        String BODY_PRODUCT_CODE = "code";
        String BODY_OTP = "otp";
        String BODY_MOBILE_NUMBER = "mobileNumber";
        String STORE_LOGO = "logo";
        String BODY_PASSWORD = "password";
    }

    interface RequestCodes {
        int TAKE_PHOTO = 100;
        int PICK_FROM_GALLERY = 101;
        int SEND_IMAGE_PATH = 102;
        int FORGOT_PASSWORD = 104;
        int CHANGE_EMAIL = 110;
        int TERMS_AND_CONDITIONS = 111;
        int ADDRESS_LOCATION = 112;
        int USER_PROFILE_SCAN = 113;
        int PRODUCT_WARRANTY_SCAN = 114;
        int PRODUCT_ASSIGN_SCAN = 115;
        int SERIAL_NO_SCAN = 116;
        int BATCH_NO_SCAN = 117;
        int ADD_NEW_MODEL = 118;
        int LOCATION_LATLNG_FROM_ADDRESS = 119;
        int LOCATION_ADDRESS_FROM_LATLNG = 120;
        int PRODUCT_ADD_FRAGMENT = 121;
        int ADD_SERVICE_CENTER = 122;
        int ADD_USER_DESIGNATION = 123;

    }

    interface TimeConstants {
        String AM_LOWER_CASE = "am";
        String AM_UPPER_CASE = "AM";
        String PM_LOWER_CASE = "pm";
        String PM_UPPER_CASE = "PM";
    }

    interface DateFormatterConstants {
        String FROM_API_MILLIS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String FROM_API = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        /* String LOCAL_DATE_DD_MM_YYYY_HH_MM_SS =
                 "dd-MM-yyyy HH:mm:ss"; //14-08-1987 18:30:00*/
        String LOCAL_DATE_DD_MM_YYYY_HH_MM =
                "dd-MM-yyyy HH:mm"; //14-08-1987 18:30
        String MMMM_YYYY = "MMMM - yyyy"; //June - 2017
        String DD_E_MMMM_YYYY = "dd-E-MMMM-yyyy"; //14-Thursday-Jun-1987
        String MM_DD_YYYY = "MM/dd/yyyy"; //14-Thursday-Jun-1987
        String DD_MM_YYYY = "dd-MM-yyyy"; //14-08-1987
        String MMMM_DD = "MMMM-dd"; //14-08-1987
        String DD_MMMM = "dd MMMM"; //14 July
        String DD_SLASH_MM_SLASH_YYYY = "dd/MM/yyyy"; //14/07/2017
        //Mon, 10 Jul 2017 10:08:20 GM
        String DDMMMM_H_MMA = "ddMMMM, h:mma"; //14July, 2:30PM
        String YYYY_MM_DD_SLASH = "yyyy/MM/dd"; //2017/01/15
        String YYYY_MM_DD = "yyyy-MM-dd"; //2017-01-15
        String MMMM_SPACE_DD = "MMMM dd"; // March 24 at 11:26am
        String HH_MM_A = "hh:mma"; // March 24 at 11:26am
        String YYYY_MMMM_DD = "yyyy-MMMM-dd"; //2017-01-15
        String YYYY_MMM_DD = "yyyy-MMM-dd"; //2017-Jan-15
        String MMM_SPACE_DD = "MMM dd"; //Mar 28
        String MMMM_DD_YYYY = "MMMM dd, yyyy"; //Oct-21-2017
        String DD_NOSPACE_MMMM = "ddMMMM"; //28March

        String TIME_HH_MM = "HH:mm";

        String UTC = "UTC";
        Locale DATE_FORMAT_LOCALE = Locale.getDefault();
    }


    interface DateDialogConstants {
        int ADD_OFFER_START_DATE = 0;
        int ADD_OFFER_END_DATE = 1;
        int ADD_OFFER_SCAN_START_DATE = 2;
        int ADD_OFFER_SCAN_END_DATE = 3;
    }

    interface ErrorCodes {
        int UNKNOWN = 0;
        int NETWORK_ERROR = 1;
        int TIMEOUT = 2;
        int NO_NETWORK = 3;
        String NEW_USER = "User not found for provided details";
    }

    interface MenuConstants {
        int PROFILE = 0;
        int ALL_SERVICE_CENTERS = 1;
        int CHANGE_PWD = 2;
        int TIMEINGS = 3;
        int CONTACTDETAILS = 4;
        int LOGOUT = 5;


    }

    interface ProductAssignValidation {
        int MODEL = 1;
        int INVALID_MODEL = 2;
        int DESCRIPTION = 3;
        int MRP_PRICE = 4;
        int PRICE = 5;

    }

    interface WarrantyregistationValidation {
        int MODEL = 1;
        int INVALID_MODEL = 2;
        int DESCRIPTION = 3;
        int SERIAL_NO = 4;
        int BATCH_NO = 5;
        int MRP_PRICE = 6;
        int PRICE = 7;
        int INVOICENUMBER = 8;
    }

    interface AddNewModelValidation {
        int MODEL = 1;
        int INVALID_MODEL = 2;
        int DESCRIPTION = 3;
        int CATEGORY = 4;
        int DIVISION = 5;
        int BRAND = 6;
        int MRP_PRICE = 7;
        int PRICE = 8;
        int NOTE = 9;
    }


    interface Favorites {
        String ADDRESS_TYPE_ONE = "1";
    }

    interface AddOfferValidation {
        int MODEL = 1;
        int INVALID_MODEL = 2;
        int CATEGORY = 3;
        int DIVISION = 4;
        int BRAND = 5;
        int OFFER_START_ON = 6;
        int OFFER_EXPIRE_ON = 7;
        int SCAN_START_DATE = 8;
        int SCAN_END_DATE = 9;
        int OFFER_PRICE = 10;

    }

    public class PushIntentConstants {
        public static final String PUSH_PAYLOAD = "pushPayload";
    }
}
