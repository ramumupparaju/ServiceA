package com.incon.service.apimodel.components.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PC on 10/2/2017.
 */

public class NotificationsResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    private String positionText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setPositionText() {
        positionText = "position :" + id;
    }

    public String getPositionText() {
        return positionText;
    }
}
