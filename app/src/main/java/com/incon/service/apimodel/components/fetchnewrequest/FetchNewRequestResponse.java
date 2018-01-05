package com.incon.service.apimodel.components.fetchnewrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.components.assigneduser.AssignedUser;
import com.incon.service.apimodel.components.customer.CustomerResponse;
import com.incon.service.apimodel.components.productinfo.ProductInfoResponse;
import com.incon.service.apimodel.components.request.RequestResponse;
import com.incon.service.dto.addservicecenter.AddServiceCenter;

/**
 * Created by MY HOME on 25-Dec-17.
 */

public class FetchNewRequestResponse {
    @SerializedName("customer")
    @Expose
    private CustomerResponse customer;
    @SerializedName("product")
    @Expose
    private ProductInfoResponse product;

    @SerializedName("serviceCenter")
    @Expose
    private AddServiceCenter serviceCenter;
    @SerializedName("request")
    @Expose
    private RequestResponse request;
    @SerializedName("assignedUser")
    @Expose
    private AssignedUser assignedUser;
    @SerializedName("productLogoUrl")
    @Expose
    private String productLogoUrl;


    @SerializedName("productImageUrl")
    @Expose
    private String productImageUrl;

    public FetchNewRequestResponse() {
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public AddServiceCenter getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(AddServiceCenter serviceCenter) {
        this.serviceCenter = serviceCenter;
    }

    public String getProductLogoUrl() {
        return productLogoUrl;
    }

    public void setProductLogoUrl(String productLogoUrl) {
        this.productLogoUrl = productLogoUrl;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    private transient boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCustomer(CustomerResponse customer) {
        this.customer = customer;
    }

    public ProductInfoResponse getProduct() {
        return product;
    }

    public void setProduct(ProductInfoResponse product) {
        this.product = product;
    }


    public RequestResponse getRequest() {
        return request;
    }

    public void setRequest(RequestResponse request) {
        this.request = request;
    }

    public AssignedUser getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(AssignedUser assignedUser) {
        this.assignedUser = assignedUser;
    }
}
