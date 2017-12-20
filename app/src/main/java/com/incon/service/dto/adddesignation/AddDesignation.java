package com.incon.service.dto.adddesignation;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.AppConstants;
import com.incon.service.utils.ValidationUtils;

/**
 * Created by PC on 12/20/2017.
 */

public class AddDesignation extends BaseObservable {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("serviceCenterId")
    @Expose
    private Integer serviceCenterId;

    private transient String serviceCenterName;
    @Bindable
    public String getServiceCenterName() {
        return serviceCenterName;
    }

    public void setServiceCenterName(String serviceCenterName) {
        this.serviceCenterName = serviceCenterName;
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
    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }

    public Integer getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(Integer serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }


    public Pair<String, Integer> validateAddDesignations(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 2; i++) {
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
                if (emptyValidation && TextUtils.isEmpty(getDescription())) {
                    return AppConstants.AddDesignationsValidation.NAME_REQ;
                }
                break;


            case 1:
                boolean nameEmpty = TextUtils.isEmpty(getName());
                if (emptyValidation && nameEmpty) {
                    return AppConstants.AddDesignationsValidation.NAME_REQ;
                }
                break;

            case 2:
                boolean serviceCenterNameEmpty = TextUtils.isEmpty(getServiceCenterName());
                if (emptyValidation && serviceCenterNameEmpty) {
                    return AppConstants.AddDesignationsValidation.SERVICE_CENTER_NAME;
                }
                break;


            default:
                return AppConstants.VALIDATION_SUCCESS;
        }
        return AppConstants.VALIDATION_SUCCESS;
    }

}