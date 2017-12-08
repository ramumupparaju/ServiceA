package com.incon.service.apimodel.components.fetchcategorie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by PC on 10/17/2017.
 */

public class Division {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("brands")
    @Expose
    private List<Brand> brands = null;

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

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

}
