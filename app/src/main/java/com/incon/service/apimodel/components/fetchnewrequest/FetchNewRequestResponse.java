package com.incon.service.apimodel.components.fetchnewrequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.incon.service.apimodel.components.assigneduser.AssignedUser;
import com.incon.service.apimodel.components.customer.Customer;
import com.incon.service.apimodel.components.productresponse.ProductInfoResponse;
import com.incon.service.apimodel.components.request.Request;
import com.incon.service.dto.registration.ServiceCenter;

/**
 * Created by MY HOME on 25-Dec-17.
 */

public class Fetchnewrequest {
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("product")
    @Expose
    private ProductInfoResponse product;
    @SerializedName("serviceCenter")
    @Expose
    private ServiceCenter serviceCenter;
    @SerializedName("request")
    @Expose
    private Request request;
    @SerializedName("assignedUser")
    @Expose
    private AssignedUser assignedUser;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ProductInfoResponse getProduct() {
        return product;
    }

    public void setProduct(ProductInfoResponse product) {
        this.product = product;
    }

    public ServiceCenter getServiceCenter() {
        return serviceCenter;
    }

    public void setServiceCenter(ServiceCenter serviceCenter) {
        this.serviceCenter = serviceCenter;
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
