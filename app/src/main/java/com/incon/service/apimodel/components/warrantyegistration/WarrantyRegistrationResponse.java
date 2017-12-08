package com.incon.service.apimodel.components.warrantyegistration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 10/5/2017.
 */

public class WarrantyRegistrationResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("modelNumber")
    @Expose
    private String modelNumber;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("divisionId")
    @Expose
    private Integer divisionId;
    @SerializedName("brandId")
    @Expose
    private Integer brandId;
    @SerializedName("typeId")
    @Expose
    private Object typeId;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("batchNumber")
    @Expose
    private String batchNumber;
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("qrCodeId")
    @Expose
    private Integer qrCodeId;
    @SerializedName("merchantId")
    @Expose
    private Integer merchantId;
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("warrantyEndDate")
    @Expose
    private Integer warrantyEndDate;
    @SerializedName("invoiceNumber")
    @Expose
    private String invoiceNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Object getTypeId() {
        return typeId;
    }

    public void setTypeId(Object typeId) {
        this.typeId = typeId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getQrCodeId() {
        return qrCodeId;
    }

    public void setQrCodeId(Integer qrCodeId) {
        this.qrCodeId = qrCodeId;
    }

    public Integer getMerchantId() {
        return merchantId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(Integer warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

}
