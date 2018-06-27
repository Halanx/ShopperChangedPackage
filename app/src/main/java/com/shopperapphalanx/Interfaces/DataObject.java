package com.shopperapphalanx.Interfaces;

/**
 * Created by Dell on 6/28/2017.
 */

public class DataObject {

    String name;
    String mobileNumber;
    String orderNo;


    public DataObject(String name, String mobileNumber, String orderNo){
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
