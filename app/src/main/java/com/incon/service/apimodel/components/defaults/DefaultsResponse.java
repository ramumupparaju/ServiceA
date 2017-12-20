package com.incon.service.apimodel.components.defaults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.components.fetchcategorie.FetchCategories;

import java.util.List;

public class DefaultsResponse {

    @SerializedName("categories")
    @Expose
    private List<FetchCategories> categoryTypeResponseList = null;

    public List<FetchCategories> getCategoryTypeResponseList() {
        return categoryTypeResponseList;
    }

    public void setCategoryTypeResponseList(List<FetchCategories> categoryTypeResponseList) {
        this.categoryTypeResponseList = categoryTypeResponseList;
    }
}