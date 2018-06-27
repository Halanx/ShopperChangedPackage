package com.shopperapphalanx.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nishant on 10/09/17.
 */

public class Customer {
    @SerializedName("PhoneNo")
    @Expose
    private String phonenumber;

    @SerializedName("user")
    @Expose
    public User user;

    public User getUser() {
        return user;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
}
