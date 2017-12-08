package com.incon.service.dto.asignqrcode;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.AppConstants;

/**
 * Created by PC on 10/12/2017.
 */

public class AssignQrCode extends BaseObservable {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("mrpPrice")
    @Expose
    private String mrpPrice;
    @SerializedName("productId")
    @Expose
    private String productId;
    private transient String description;
    private transient String productName;

    public String getMrpPrice() {
        return mrpPrice;
    }

    public void setMrpPrice(String mrpPrice) {
        this.mrpPrice = mrpPrice;
    }

    @Bindable
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        notifyChange();
    }
    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyChange();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Pair<String, Integer> validateProductModel(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 3; i++) {
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
                boolean modelEmpty = TextUtils.isEmpty(productName);
                if (emptyValidation && modelEmpty) {
                    return AppConstants.ProductAssignValidation.MODEL;
                } else if (!modelEmpty && TextUtils.isEmpty(productId)) {
                    return AppConstants.ProductAssignValidation.INVALID_MODEL;
                }
                break;

            case 1:
                boolean descEmpty = TextUtils.isEmpty(description);
                if (emptyValidation && descEmpty) {
                    return AppConstants.ProductAssignValidation.DESCRIPTION;
                }
                break;
            case 2:
                boolean mrpPriceEmpty = TextUtils.isEmpty(mrpPrice);
                if (emptyValidation && mrpPriceEmpty) {
                    return AppConstants.ProductAssignValidation.MRP_PRICE;
                }
                break;
            case 3:
                boolean priceEmpty = TextUtils.isEmpty(price);
                if (emptyValidation && priceEmpty) {
                    return AppConstants.ProductAssignValidation.PRICE;
                }
                break;

            default:
                return AppConstants.VALIDATION_SUCCESS;
        }
        return AppConstants.VALIDATION_SUCCESS;
    }
}