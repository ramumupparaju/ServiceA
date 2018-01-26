package com.incon.service.apimodel.components.productinfo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MY HOME on 25-Dec-17.
 */

public class Product extends BaseObservable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("modelNo")
    @Expose
    private String modelNo;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("specialInstruction")
    @Expose
    private String specialInstruction;
    @SerializedName("suggestionSubmitted")
    @Expose
    private Boolean suggestionSubmitted;
    @SerializedName("reviewSubmitted")
    @Expose
    private Boolean reviewSubmitted;
    @SerializedName("feedbackSubmitted")
    @Expose
    private Boolean feedbackSubmitted;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyChange();
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public Boolean getSuggestionSubmitted() {
        return suggestionSubmitted;
    }

    public void setSuggestionSubmitted(Boolean suggestionSubmitted) {
        this.suggestionSubmitted = suggestionSubmitted;
    }

    public Boolean getReviewSubmitted() {
        return reviewSubmitted;
    }

    public void setReviewSubmitted(Boolean reviewSubmitted) {
        this.reviewSubmitted = reviewSubmitted;
    }

    public Boolean getFeedbackSubmitted() {
        return feedbackSubmitted;
    }

    public void setFeedbackSubmitted(Boolean feedbackSubmitted) {
        this.feedbackSubmitted = feedbackSubmitted;
    }
}
