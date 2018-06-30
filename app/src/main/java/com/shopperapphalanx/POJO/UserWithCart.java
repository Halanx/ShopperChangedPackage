package com.shopperapphalanx.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samarthgupta on 19/07/17.
 */

public class UserWithCart {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customer")
    @Expose
    public Customer customer;
    @SerializedName("total")
    @Expose
    private Double subtotal;
    @SerializedName("tax")
    @Expose
    private Double taxes;
    @SerializedName("estimated_delivery_charges")
    @Expose
    private Double estimatedeliverycharge;
    @SerializedName("total_with_extras")
    @Expose
    private Double total;
    @SerializedName("DeliveryCharges")
    @Expose
    private Double deliverycharge;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("Active")
    @Expose
    private Boolean active;

    public UserWithCart() {
    }


    public Integer getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Double getTaxes() {
        return taxes;
    }

    public Double getEstimatedeliverycharge() {
        return estimatedeliverycharge;
    }

    public void setEstimatedeliverycharge(Double estimatedeliverycharge) {
        this.estimatedeliverycharge = estimatedeliverycharge;
    }

    public Double getTotal() {
        return total;
    }

    public Double getDeliverycharge() {
        return deliverycharge;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Boolean getActive() {
        return active;
    }
}

