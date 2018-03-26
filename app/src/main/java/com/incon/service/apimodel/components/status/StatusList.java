package com.incon.service.apimodel.components.status;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.components.assigneduser.AssignedUser;
import com.incon.service.apimodel.components.customer.Customer;
import com.incon.service.apimodel.components.productinfo.Product;
import com.incon.service.dto.registration.ServiceCenter;
import com.incon.service.dto.servicerequest.ServiceRequest;

public class StatusList {

    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("serviceCenter")
    @Expose
    private ServiceCenter serviceCenter;
    @SerializedName("request")
    @Expose
    private ServiceRequest request;
    @SerializedName("assignedUser")
    @Expose
    private AssignedUser assignedUser;
    @SerializedName("preferredUser")
    @Expose
    private PreferredUser preferredUser;

    public Customer getCustomer() {
        return customer;
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

    public ServiceCenter getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(ServiceCenter serviceCenter) {
        this.serviceCenter = serviceCenter;
    }

    public ServiceRequest getRequest() {
        return request;
    }

    public void setRequest(ServiceRequest request) {
        this.request = request;
    }

    public AssignedUser getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(AssignedUser assignedUser) {
        this.assignedUser = assignedUser;
    }

    public PreferredUser getPreferredUser() {
        return preferredUser;
    }

    public void setPreferredUser(PreferredUser preferredUser) {
        this.preferredUser = preferredUser;
    }

}