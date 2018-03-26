package com.incon.service.apimodel.components.fetchnewrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.components.assigneduser.AssignedUser;
import com.incon.service.apimodel.components.customer.Customer;
import com.incon.service.apimodel.components.productinfo.Product;
import com.incon.service.apimodel.components.request.Request;
import com.incon.service.apimodel.components.status.StatusList;
import com.incon.service.dto.addservicecenter.AddServiceCenter;

import java.util.List;

/**
 * Created by MY HOME on 25-Dec-17.
 */

public class FetchNewRequestResponse {
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("product")
    @Expose
    private Product product;

    @SerializedName("serviceCenter")
    @Expose
    private AddServiceCenter serviceCenter;
    @SerializedName("request")
    @Expose
    private Request request;
    @SerializedName("assignedUser")
    @Expose
    private AssignedUser assignedUser;
    @SerializedName("productLogoUrl")
    @Expose
    private String productLogoUrl;


    @SerializedName("productImageUrl")
    @Expose
    private String productImageUrl;

    @SerializedName("statusList")
    @Expose
    private List<StatusList> statusList = null;

    public List<StatusList> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<StatusList> statusList) {
        this.statusList = statusList;
    }

    public FetchNewRequestResponse() {
    }

    public Customer getCustomer() {
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

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public AssignedUser getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(AssignedUser assignedUser) {
        this.assignedUser = assignedUser;
    }
}
