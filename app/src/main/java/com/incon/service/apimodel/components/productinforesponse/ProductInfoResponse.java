package com.incon.service.apimodel.components.productinforesponse;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.components.qrcodebaruser.Store;

/**
 * Created by PC on 11/8/2017.
 */

public class ProductInfoResponse extends BaseObservable implements Parcelable {


    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("codeId")
    @Expose
    private Integer codeId;
    @SerializedName("batchCode")
    @Expose
    private String batchCode;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("batchNumber")
    @Expose
    private String batchNumber;
    @SerializedName("productModel")
    @Expose
    private String productModel;

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("productId")
    @Expose
    private Integer productId;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("modelNumber")
    @Expose
    private String modelNumber;
    @SerializedName("division")
    @Expose
    private String division;
    @SerializedName("divisionId")
    @Expose
    private Integer divisionId;
    @SerializedName("information")
    @Expose
    private String information;
    @SerializedName("brandId")
    @Expose
    private String brandId;
    @SerializedName("brandName")
    @Expose
    private String brandName;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("warrantyId")
    @Expose
    private String warrantyId;
    @SerializedName("invoiceNumber")
    @Expose
    private String invoiceNumber;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusDate")
    @Expose
    private Long statusDate;
    @SerializedName("warrantyEndDate")
    @Expose
    private Long warrantyEndDate;
    @SerializedName("productLogoUrl")
    @Expose
    private String productLogoUrl;
    @SerializedName("productImageUrl")
    @Expose
    private String productImageUrl;
    @SerializedName("productQrCode")
    @Expose
    private String productQrCode;
    @SerializedName("mrp")
    @Expose
    private Integer mrp;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("customerContact")
    @Expose
    private String customerContact;
    @SerializedName("interestId")
    @Expose
    private Integer interestId;
    @SerializedName("qrcodeId")
    @Expose
    private Integer qrcodeId;
    @SerializedName("merchantId")
    @Expose
    private Integer merchantId;
    @SerializedName("requestedDate")
    @Expose
    private long requestedDate;

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("addressId")
    @Expose
    private String addressId;

    @SerializedName("specialInstruction")
    @Expose
    private String specialInstruction;
    @SerializedName("returnPolicy")
    @Expose
    private String returnPolicy;
    @SerializedName("purchasedDate")
    @Expose
    private long purchasedDate;
    @SerializedName("storeLocation")
    @Expose
    private String storeLocation;
    @SerializedName("storeContactNumber")
    @Expose
    private String storeContactNumber;
    @SerializedName("storeName")
    @Expose
    private String storeName;
    @SerializedName("storeAddress")
    @Expose
    private String storeAddress;
    //    for interest
    @SerializedName("warrantyYears")
    @Expose
    private Integer warrantyYears;
    @SerializedName("warrantyMonths")
    @Expose
    private Integer warrantyMonths;
    @SerializedName("warrantyDays")
    @Expose
    private Integer warrantyDays;
    @SerializedName("warrantyConditions")
    @Expose
    private String warrantyConditions;

    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("productSpecification")
    @Expose
    private String productSpecification;

    @SerializedName("productDimensions")
    @Expose
    private String productDimensions;
    @SerializedName("mainFeatures")
    @Expose
    private String mainFeatures;
    @SerializedName("merchantComments")
    @Expose
    private String merchantComments;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customerName")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("msisdn")
    @Expose
    private String msisdn;
    @SerializedName("usertype")
    @Expose
    private Integer usertype;
    @SerializedName("store")
    @Expose
    private Store store;
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("returnDate")
    @Expose
    private long returnDate;
    public static Creator<ProductInfoResponse> getCREATOR() {
        return CREATOR;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }
    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getStoreAddress() {
        return storeAddress;
    }


    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getProductDimensions() {
        return productDimensions;
    }

    public void setProductDimensions(String productDimensions) {
        this.productDimensions = productDimensions;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    private transient boolean isSelected;


    public ProductInfoResponse() {

    }

    @Bindable
    public String getMerchantComments() {
        return merchantComments;
    }

    public void setMerchantComments(String merchantComments) {
        this.merchantComments = merchantComments;
        notifyChange();
    }

    @Bindable
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
        notifyChange();
    }

    public String getMainFeatures() {
        return mainFeatures;
    }

    public void setMainFeatures(String mainFeatures) {
        this.mainFeatures = mainFeatures;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Integer getUsertype() {
        return usertype;
    }

    public void setUsertype(Integer usertype) {
        this.usertype = usertype;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Long statusDate) {
        this.statusDate = statusDate;
    }

    public Integer getQrcodeId() {
        return qrcodeId;
    }

    public Integer getWarrantyYears() {
        return warrantyYears;
    }

    public void setWarrantyYears(Integer warrantyYears) {
        this.warrantyYears = warrantyYears;
    }

    public Integer getWarrantyMonths() {
        return warrantyMonths;
    }

    public void setWarrantyMonths(Integer warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }

    public Integer getWarrantyDays() {
        return warrantyDays;
    }

    public void setWarrantyDays(Integer warrantyDays) {
        this.warrantyDays = warrantyDays;
    }

    public String getWarrantyConditions() {
        return warrantyConditions;
    }

    public void setWarrantyConditions(String warrantyConditions) {
        this.warrantyConditions = warrantyConditions;
    }

    public void setQrcodeId(Integer qrcodeId) {
        this.qrcodeId = qrcodeId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public long getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(long requestedDate) {
        this.requestedDate = requestedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public Object getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getCategory() {
        return category;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public String getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(String returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public long getPurchasedDate() {
        return purchasedDate;
    }

    public void setPurchasedDate(long purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getStoreContactNumber() {
        return storeContactNumber;
    }

    public void setStoreContactNumber(String storeContactNumber) {
        this.storeContactNumber = storeContactNumber;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public Integer getInterestId() {
        return interestId;
    }

    public void setInterestId(Integer interestId) {
        this.interestId = interestId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Bindable
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        notifyChange();
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWarrantyId() {
        return warrantyId;
    }

    public void setWarrantyId(String warrantyId) {
        this.warrantyId = warrantyId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(Long warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public String getProductLogoUrl() {
        return productLogoUrl;
    }

    public void setProductLogoUrl(String productLogoUrl) {
        this.productLogoUrl = productLogoUrl;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductQrCode() {
        return productQrCode;
    }

    public void setProductQrCode(String productQrCode) {
        this.productQrCode = productQrCode;
    }

    public Integer getMrp() {
        return mrp;
    }

    public void setMrp(Integer mrp) {
        this.mrp = mrp;
    }

    public long getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(long returnDate) {
        this.returnDate = returnDate;
    }

    protected ProductInfoResponse(Parcel in) {
        serialNumber = in.readString();
        codeId = in.readByte() == 0x00 ? null : in.readInt();
        batchCode = in.readString();
        mobileNumber = in.readString();
        batchNumber = in.readString();
        productModel = in.readString();
        category = in.readString();
        productId = in.readByte() == 0x00 ? null : in.readInt();
        categoryId = in.readByte() == 0x00 ? null : in.readInt();
        productName = in.readString();
        modelNumber = in.readString();
        division = in.readString();
        divisionId = in.readByte() == 0x00 ? null : in.readInt();
        information = in.readString();
        brandId = in.readString();
        brandName = in.readString();
        price = in.readString();
        warrantyId = in.readString();
        invoiceNumber = in.readString();
        status = in.readString();
        statusDate = in.readByte() == 0x00 ? null : in.readLong();
        warrantyEndDate = in.readByte() == 0x00 ? null : in.readLong();
        productLogoUrl = in.readString();
        productImageUrl = in.readString();
        productQrCode = in.readString();
        mrp = in.readByte() == 0x00 ? null : in.readInt();
        address = in.readString();
        interestId = in.readByte() == 0x00 ? null : in.readInt();
        qrcodeId = in.readByte() == 0x00 ? null : in.readInt();
        merchantId = in.readByte() == 0x00 ? null : in.readInt();
        requestedDate = in.readLong();
        returnDate = in.readLong();
        userId = in.readString();
        addressId = in.readString();
        specialInstruction = in.readString();
        returnPolicy = in.readString();
        purchasedDate = in.readLong();
        storeLocation = in.readString();
        storeContactNumber = in.readString();
        storeName = in.readString();
        storeAddress = in.readString();
        warrantyYears = in.readByte() == 0x00 ? null : in.readInt();
        warrantyMonths = in.readByte() == 0x00 ? null : in.readInt();
        warrantyDays = in.readByte() == 0x00 ? null : in.readInt();
        warrantyConditions = in.readString();
        color = in.readString();
        productSpecification = in.readString();
        productDimensions = in.readString();
        mainFeatures = in.readString();
        isSelected = in.readByte() != 0x00;
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        email = in.readString();
        location = in.readString();
        msisdn = in.readString();
        usertype = in.readByte() == 0x00 ? null : in.readInt();
        store = (Store) in.readValue(Store.class.getClassLoader());
        uuid = in.readString();
        country = in.readString();
        dob = in.readString();
        gender = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(serialNumber);
        if (codeId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(codeId);
        }
        dest.writeValue(batchCode);
        dest.writeString(mobileNumber);
        dest.writeString(batchNumber);
        dest.writeString(productModel);
        dest.writeString(category);
        if (productId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(productId);
        }
        if (categoryId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(categoryId);
        }
        dest.writeString(productName);
        dest.writeString(modelNumber);
        dest.writeString(division);
        if (divisionId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(divisionId);
        }
        dest.writeString(information);
        dest.writeString(brandId);
        dest.writeString(brandName);
        dest.writeString(price);
        dest.writeString(warrantyId);
        dest.writeString(invoiceNumber);
        dest.writeString(status);
        if (statusDate == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(statusDate);
        }
        if (warrantyEndDate == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeLong(warrantyEndDate);
        }
        dest.writeString(productLogoUrl);
        dest.writeString(productImageUrl);
        dest.writeString(productQrCode);
        if (mrp == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(mrp);
        }
        dest.writeString(address);
        if (interestId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(interestId);
        }
        if (qrcodeId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(qrcodeId);
        }
        if (merchantId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(merchantId);
        }
        dest.writeLong(requestedDate);
        dest.writeLong(returnDate);
        dest.writeString(userId);
        dest.writeString(addressId);
        dest.writeString(specialInstruction);
        dest.writeString(returnPolicy);
        dest.writeLong(purchasedDate);
        dest.writeString(storeLocation);
        dest.writeString(storeContactNumber);
        dest.writeString(storeName);
        dest.writeString(storeAddress);
        if (warrantyYears == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(warrantyYears);
        }
        if (warrantyMonths == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(warrantyMonths);
        }
        if (warrantyDays == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(warrantyDays);
        }
        dest.writeString(warrantyConditions);
        dest.writeString(color);
        dest.writeString(productSpecification);
        dest.writeString(productDimensions);
        dest.writeString(mainFeatures);
        dest.writeByte((byte) (isSelected ? 0x01 : 0x00));
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(location);
        dest.writeString(msisdn);
        if (usertype == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(usertype);
        }
        dest.writeValue(store);
        dest.writeString(uuid);
        dest.writeString(country);
        dest.writeString(dob);
        dest.writeString(gender);
    }

    @SuppressWarnings("unused")
    public static final Creator<ProductInfoResponse> CREATOR = new Creator<ProductInfoResponse>() {
        @Override
        public ProductInfoResponse createFromParcel(Parcel in) {
            return new ProductInfoResponse(in);
        }

        @Override
        public ProductInfoResponse[] newArray(int size) {
            return new ProductInfoResponse[size];
        }
    };
}