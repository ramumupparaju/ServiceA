package com.incon.service.dto.addoffer;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Pair;

import com.incon.service.AppConstants;


public class AddOfferRequest extends BaseObservable {

    private String brandId;
    private String categoryId;
    private String customerId;
    private String divisionId;
    private String offerStartOn;
    private String merchantId;
    private String modelNumber;
    private String offer;
    private String productId;
    private String purchaseId;
    private String offerEndOn;
    private String scanStartDate;
    private String scanEndDate;
    private transient String productBrand;
    private transient String categoryName;
    private transient String divisionName;

    public AddOfferRequest() {

    }

    public AddOfferRequest(String brandId, String categoryId, String customerId,
                           String divisionId, String offerStartOn, String merchantId,
                           String modelNumber, String offer, String productId,
                           String purchaseId, String offerEndOn) {
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.customerId = customerId;
        this.divisionId = divisionId;
        this.offerStartOn = offerStartOn;
        this.merchantId = merchantId;
        this.modelNumber = modelNumber;
        this.offer = offer;
        this.productId = productId;
        this.purchaseId = purchaseId;
        this.offerEndOn = offerEndOn;
    }


    @Bindable
    public String getScanStartDate() {
        return scanStartDate;
    }

    public void setScanStartDate(String scanStartDate) {
        this.scanStartDate = scanStartDate;
        notifyChange();
    }

    @Bindable
    public String getScanEndDate() {
        return scanEndDate;
    }

    public void setScanEndDate(String scanEndDate) {
        this.scanEndDate = scanEndDate;
        notifyChange();
    }



    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    @Bindable
    public String getFromDate() {
        return offerStartOn;
    }

    public void setFromDate(String fromDate) {
        this.offerStartOn = fromDate;
        notifyChange();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    @Bindable
    public String getToDate() {
        return offerEndOn;
    }

    public void setToDate(String toDate) {
        this.offerEndOn = toDate;
        notifyChange();
    }


    public Pair<String, Integer> validateAddOffer(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 8; i++) {
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
                boolean modelEmpty = TextUtils.isEmpty(modelNumber);
                if (emptyValidation && modelEmpty) {
                    return AppConstants.AddOfferValidation.MODEL;
                } else if (!modelEmpty && TextUtils.isEmpty(productId)) {
                    return AppConstants.AddOfferValidation.INVALID_MODEL;
                }
                break;

            case 1:
                boolean categoryEmpty = TextUtils.isEmpty(categoryName);
                if (emptyValidation && categoryEmpty) {
                    return AppConstants.AddOfferValidation.CATEGORY;
                }
                break;
            case 2:
                boolean divisionEmpty = TextUtils.isEmpty(divisionName);
                if (emptyValidation && divisionEmpty) {
                    return AppConstants.AddOfferValidation.DIVISION;
                }
                break;

            case 4:
                boolean brandEmpty = TextUtils.isEmpty(productBrand);
                if (emptyValidation && brandEmpty) {
                    return AppConstants.AddOfferValidation.BRAND;
                }
                break;

            case 5:
                boolean offerStartOnEmpty = TextUtils.isEmpty(offerStartOn);
                if (emptyValidation && offerStartOnEmpty) {
                    return AppConstants.AddOfferValidation.OFFER_START_ON;
                }
                break;


            case 6:
                boolean offerEndOnEmpty = TextUtils.isEmpty(offerEndOn);
                if (emptyValidation && offerEndOnEmpty) {
                    return AppConstants.AddOfferValidation.OFFER_EXPIRE_ON;
                }
                break;
            case 7:
                boolean scanStartOnEmpty = TextUtils.isEmpty(scanStartDate);
                if (emptyValidation && scanStartOnEmpty) {
                    return AppConstants.AddOfferValidation.SCAN_START_DATE;
                }
                break;
            case 8:
                boolean scanEndOnEmpty = TextUtils.isEmpty(scanEndDate);
                if (emptyValidation && scanEndOnEmpty) {
                    return AppConstants.AddOfferValidation.SCAN_END_DATE;
                }
                break;

            case 9:
                boolean offerPriceEmpty = TextUtils.isEmpty(offer);
                if (emptyValidation && offerPriceEmpty) {
                    return AppConstants.AddOfferValidation.OFFER_PRICE;
                }
                break;

            default:
                return AppConstants.VALIDATION_SUCCESS;
        }
        return AppConstants.VALIDATION_SUCCESS;
    }




}