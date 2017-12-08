package com.incon.service.apimodel.components.search;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelSearchResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("information")
    @Expose
    private String information;
    @SerializedName("directions")
    @Expose
    private Object directions;
    @SerializedName("warnings")
    @Expose
    private Object warnings;
    @SerializedName("warranty")
    @Expose
    private String warranty;
    @SerializedName("modelNumber")
    @Expose
    private String modelNumber;
    @SerializedName("entryPoint")
    @Expose
    private Object entryPoint;
    @SerializedName("category")
    @Expose
    private Category category;

    private Division division;

    @SerializedName("type")
    @Expose
    private ModelType type;
    @SerializedName("productCode")
    @Expose
    private String productCode;
    @SerializedName("color")
    @Expose
    private Object color;
    @SerializedName("size")
    @Expose
    private Object size;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("item")
    @Expose
    private String item;
    @SerializedName("logoUrl")
    @Expose
    private String logoUrl;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("notes")
    @Expose
    private String notes;

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Object getDirections() {
        return directions;
    }

    public void setDirections(Object directions) {
        this.directions = directions;
    }

    public Object getWarnings() {
        return warnings;
    }

    public void setWarnings(Object warnings) {
        this.warnings = warnings;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public Object getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(Object entryPoint) {
        this.entryPoint = entryPoint;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ModelType getType() {
        return type;
    }

    public void setType(ModelType type) {
        this.type = type;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Object getColor() {
        return color;
    }

    public void setColor(Object color) {
        this.color = color;
    }

    public Object getSize() {
        return size;
    }

    public void setSize(Object size) {
        this.size = size;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return modelNumber;
    }

    protected ModelSearchResponse(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        information = in.readString();
        directions = (Object) in.readValue(Object.class.getClassLoader());
        warnings = (Object) in.readValue(Object.class.getClassLoader());
        warranty = in.readString();
        modelNumber = in.readString();
        entryPoint = (Object) in.readValue(Object.class.getClassLoader());
        category = (Category) in.readValue(Category.class.getClassLoader());
        division = (Division) in.readValue(Division.class.getClassLoader());
        type = (ModelType) in.readValue(ModelType.class.getClassLoader());
        productCode = in.readString();
        color = (Object) in.readValue(Object.class.getClassLoader());
        size = (Object) in.readValue(Object.class.getClassLoader());
        price = in.readString();
        item = in.readString();
        logoUrl = in.readString();
        imageUrl = in.readString();
        notes = in.readString();
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
        dest.writeString(information);
        dest.writeValue(directions);
        dest.writeValue(warnings);
        dest.writeString(warranty);
        dest.writeString(modelNumber);
        dest.writeValue(entryPoint);
        dest.writeValue(category);
        dest.writeValue(division);
        dest.writeValue(type);
        dest.writeString(productCode);
        dest.writeValue(color);
        dest.writeValue(size);
        dest.writeString(price);
        dest.writeString(item);
        dest.writeString(logoUrl);
        dest.writeString(imageUrl);
        dest.writeString(notes);
    }

    @SuppressWarnings("unused")
    public static final Creator<ModelSearchResponse> CREATOR =
            new Creator<ModelSearchResponse>() {
                @Override
                public ModelSearchResponse createFromParcel(Parcel in) {
                    return new ModelSearchResponse(in);
                }

                @Override
                public ModelSearchResponse[] newArray(int size) {
                    return new ModelSearchResponse[size];
                }
            };
}