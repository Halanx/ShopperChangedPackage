package com.shopperapphalanx.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nishant on 10/09/17.
 */

public class User {
    @SerializedName("first_name")
    @Expose
    public String first_name;
    @SerializedName("last_name")
    @Expose
    public String last_name;

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }
}
