package com.incon.service.apimodel.components.defaults;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("insertTimestamp")
    @Expose
    private long insertTimestamp;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(long insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object categoryName) {
        if (categoryName instanceof CategoryResponse) {
            CategoryResponse categoryName1 = ((CategoryResponse) categoryName);
            if (categoryName1 != null) {
                return categoryName1.getName().equals(name);
            }
        }
        return false;
    }
}