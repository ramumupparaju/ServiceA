package com.incon.service.apimodel.components.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegistrationDefaultsData {

    @SerializedName("contact_number_types")
    @Expose
    private List<ContactNumberTypeResponse> contactNumberTypeId = null;
    @SerializedName("gender")
    @Expose
    private List<GenderResponse> gender = null;
    @SerializedName("timezone")
    @Expose
    private List<RegistrationTimezone> timezone = null;

    public List<ContactNumberTypeResponse> getContactNumberTypeId() {
        return contactNumberTypeId;
    }

    public void setContactNumberTypeId(List<ContactNumberTypeResponse> contactNumberTypeId) {
        this.contactNumberTypeId = contactNumberTypeId;
    }

    public List<GenderResponse> getGender() {
        return gender;
    }

    public void setGender(List<GenderResponse> gender) {
        this.gender = gender;
    }

    public List<RegistrationTimezone> getTimezone() {
        return timezone;
    }

    public void setTimezone(List<RegistrationTimezone> timezone) {
        this.timezone = timezone;
    }

}
