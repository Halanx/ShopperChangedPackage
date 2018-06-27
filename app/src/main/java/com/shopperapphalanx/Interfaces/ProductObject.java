package com.shopperapphalanx.Interfaces;

/**
 * Created by Dell on 6/29/2017.
 */

public class ProductObject {
    String productName;
    String productPrice;
    String instructions;

    public ProductObject(String productName, String productPrice, String instructions){
        this.productName = productName;
        this.productPrice = productPrice;
        this.instructions = instructions;

    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
