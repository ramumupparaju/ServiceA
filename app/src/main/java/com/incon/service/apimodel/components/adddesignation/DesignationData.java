package com.incon.service.apimodel.components.adddesignation;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.AppConstants;

/**
 * Created by PC on 12/20/2017.
 */

public class DesignationData extends BaseObservable implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("serviceCenterId")
    @Expose
    private Integer serviceCenterId;
    @SerializedName("isAdmin")
    @Expose
    private Integer isAdmin;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DesignationData() {
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getServiceCenterId() {
        return serviceCenterId;
    }

    public void setServiceCenterId(Integer serviceCenterId) {
        this.serviceCenterId = serviceCenterId;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    protected DesignationData(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        description = in.readString();
        serviceCenterId = in.readByte() == 0x00 ? null : in.readInt();
        isAdmin = in.readByte() == 0x00 ? null : in.readInt();
        createdBy = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(description);
        if (serviceCenterId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(serviceCenterId);
        }if (isAdmin == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(isAdmin);
        }
        if (createdBy == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(createdBy);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<DesignationData> CREATOR = new Parcelable.Creator<DesignationData>() {
        @Override
        public DesignationData createFromParcel(Parcel in) {
            return new DesignationData(in);
        }

        @Override
        public DesignationData[] newArray(int size) {
            return new DesignationData[size];
        }
    };



    public Pair<String, Integer> validateAddDesignations(String tag) {

        int fieldId = AppConstants.VALIDATION_FAILURE;
        if (tag == null) {
            for (int i = 0; i <= 1; i++) {
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
                if (emptyValidation && TextUtils.isEmpty(getName())) {
                    return AppConstants.AddDesignationsValidation.NAME_REQ;
                }
                break;


            case 1:
                boolean decEmpty = TextUtils.isEmpty(getDescription());
                if (emptyValidation && decEmpty) {
                    return AppConstants.AddDesignationsValidation.DESCRIPTION;
                }
                break;

            default:
                return AppConstants.VALIDATION_SUCCESS;
        }
        return AppConstants.VALIDATION_SUCCESS;
    }
}