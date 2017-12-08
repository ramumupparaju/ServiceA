package com.incon.service.apimodel.components.addoffer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddOfferMerchantFragmentResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("brandId")
    @Expose
    private Integer brandId;
    @SerializedName("divisionId")
    @Expose
    private Integer divisionId;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("modelNumber")
    @Expose
    private String modelNumber;
    @SerializedName("offer")
    @Expose
    private Integer offer;
    @SerializedName("fromDate")
    @Expose
    private long fromDate;
    @SerializedName("toDate")
    @Expose
    private long toDate;
    @SerializedName("merchantId")
    @Expose
    private Integer merchantId;
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("purchaseId")
    @Expose
    private Integer purchaseId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public Integer getOffer() {
        return offer;
    }

    public void setOffer(Integer offer) {
        this.offer = offer;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public long getFromDate() {
        return fromDate;
    }

    public void setFromDate(long fromDate) {
        this.fromDate = fromDate;
    }

    public long getToDate() {
        return toDate;
    }

    public void setToDate(long toDate) {
        this.toDate = toDate;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

}