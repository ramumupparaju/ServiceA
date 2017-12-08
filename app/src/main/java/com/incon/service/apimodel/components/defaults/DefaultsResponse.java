package com.incon.service.apimodel.components.defaults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefaultsResponse {

    @SerializedName("types")
    @Expose
    private List<CategoryTypeResponse> types = null;
    @SerializedName("categories")
    @Expose
    private List<CategoryResponse> categories = null;

    public List<CategoryTypeResponse> getTypes() {
        return types;
    }

    public void setTypes(List<CategoryTypeResponse> types) {
        this.types = types;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponse> categories) {
        this.categories = categories;
    }

}