package com.shopperapphalanx.Interfaces;

/**
 * Created by Dell on 6/30/2017.
 */

public class EarningObject {
    String batchNo;
    Object noOfOrders;
    Object income;

    public EarningObject(String batchNo, Object noOfOrders, Object income){
        this.batchNo = batchNo;
        this.noOfOrders = noOfOrders;
        this.income = income;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public Object getIncome() {
        return income;
    }

    public Object getNoOfOrders() {
        return noOfOrders;
    }

    }
