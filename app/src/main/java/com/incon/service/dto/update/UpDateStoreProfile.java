package com.incon.service.dto.update;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.util.Pair;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.AppConstants;

/**
 * Created by PC on 10/24/2017.
 */

public class UpDateStoreProfile extends BaseObservable {


    @SerializedName("contactNumber")
    @Expose
    private String contactNumber;
    @SerializedName("gstn")
    @Expose
    private String gstn;
    @SerializedName("storeAddress")
    @Expose
    private String storeAddress;
    @SerializedName("storeEmail")
    @Expose
    private String storeEmail;
    @SerializedName("storeLocation")
    @Expose
    private String storeLocation;
    @SerializedName("storeName")
    @Expose
    private String storeName;

    private String storeCategoryNames;
    private Integer storeId;

    private String logo;

    public UpDateStoreProfile() {

    }


    @Bindable
    public String getStoreCategoryNames() {
        return storeCategoryNames;
    }

    public void setStoreCategoryNames(String storeCategoryNames) {
        this.storeCategoryNames = storeCategoryNames;
        notifyChange();
    }

    @Bindable
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        notifyChange();
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }


    @Bindable
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Bindable
    public String getGstn() {
        return gstn;
    }

    public void setGstn(String gstn) {
        this.gstn = gstn;
        notifyChange();
    }

    @Bindable
    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
        notifyChange();
    }

    @Bindable
    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
        notifyChange();
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }


    public Pair<String, Integer> validateUpDateStoreProfile(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 5; i++) {
                fieldId = validateFields(i, true);
                if (fieldId != AppConstants.VALIDATION_SUCCESS) {
                    tag = i + "";
                    break;
                }
            }
        } else {
            fieldId = validateFields(Integer.parseInt(tag), false);
        }

        return new Pair<>(tag, fieldId);
    }

    private int validateFields(int id, boolean emptyValidation) {
        switch (id) {
            case 0:
                boolean nameEmpty = TextUtils.isEmpty(storeName);
                if (emptyValidation && nameEmpty) {
                    return AppConstants.RegistrationValidation.NAME_REQ;
                }
                break;

            case 1:
                boolean phoneNumberEmpty = TextUtils.isEmpty(contactNumber);
                if (emptyValidation && phoneNumberEmpty) {
                    return AppConstants.RegistrationValidation.PHONE_REQ;
                }
                break;

            case 2:
                boolean categoryEmpty = TextUtils.isEmpty(storeCategoryNames);
                if (emptyValidation && categoryEmpty) {
                    return AppConstants.RegistrationValidation.CATEGORY_REQ;
                }
                break;

            case 3:
                boolean addressEmpty = TextUtils.isEmpty(storeAddress);
                if (emptyValidation && addressEmpty) {
                    return AppConstants.RegistrationValidation.ADDRESS_REQ;
                }
                break;
            case 4:
                boolean userEmailEmpty = TextUtils.isEmpty(storeEmail);
                if (emptyValidation && userEmailEmpty) {
                    return AppConstants.RegistrationValidation.EMAIL_REQ;
                }
                break;


            case 5:
                boolean gstnEmpty = TextUtils.isEmpty(gstn);
                if (emptyValidation && gstnEmpty) {
                    return AppConstants.RegistrationValidation.GSTN_REQ;
                }
                break;

            default:
                return AppConstants.VALIDATION_SUCCESS;
        }
        return AppConstants.VALIDATION_SUCCESS;
    }

    @BindingAdapter({"bind:imageapi"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl)
                .into(imageView);
    }

}
