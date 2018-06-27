package com.shopperapphalanx.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;

/**
 * Created by samarthgupta on 19/07/17.
 */

public class BatchItem implements Comparator<BatchItem> {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Cart")
    @Expose
    public UserWithCart cartUser;
    @SerializedName("Item")
    @Expose
    private Item item;
    @SerializedName("OrderId")
    @Expose
    private OrderId orderIdId;
    @SerializedName("RemovedFromCart")
    @Expose
    private Boolean removedFromCart;
    @SerializedName("IsOrdered")
    @Expose
    private Boolean isOrdered;
    @SerializedName("Quantity")
    @Expose
    private Double quantity;
    @SerializedName("SubTotal")
    @Expose
    private Double subTotal;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("Active")
    @Expose
    private Boolean active;
    @SerializedName("BatchId")
    @Expose
    private Integer batchId;

    @SerializedName("SubscriptionId")
    @Expose
    private OrderId subscriptionid;

    public Integer getId() {
        return id;
    }

    public UserWithCart getCartUser() {
        return cartUser;
    }

    public Item getItem() {
        return item;
    }

    public OrderId getOrderIdId() {
        return orderIdId;
    }


    public Boolean getRemovedFromCart() {
        return removedFromCart;
    }

    public Boolean getOrdered() {
        return isOrdered;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public String getNotes() {
        return notes;
    }

    public Boolean getActive() {
        return active;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public OrderId getSubscriptionid() {
        return subscriptionid;
    }

    public void setSubscriptionid(OrderId subscriptionid) {
        this.subscriptionid = subscriptionid;
    }

    @Override
    public int compare(BatchItem b1, BatchItem b2) {
        return new CompareToBuilder().append(b1.getItem().getRelatedStore().getId(),b2.getItem().getRelatedStore().getId()).
                append(b1.cartUser.customer.getPhonenumber(),b2.cartUser.customer.getPhonenumber()).toComparison();
    }
}