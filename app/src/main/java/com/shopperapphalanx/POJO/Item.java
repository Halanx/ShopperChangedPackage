package com.shopperapphalanx.POJO;

/**
 * Created by samarthgupta on 19/07/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("store")
    @Expose
    private RelatedStore relatedStore;
    @SerializedName("name")
    @Expose
    private String productName;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("StoreId")
    @Expose
    private Integer storeId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("product_image_url")
    @Expose
    private String productImage;
    @SerializedName("features")
    @Expose
    private String features;
    @SerializedName("size")
    @Expose
    private Integer productSize;
    @SerializedName("Active")
    @Expose
    private Boolean active;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RelatedStore getRelatedStore() {
        return relatedStore;
    }

    public void setRelatedStore(RelatedStore relatedStore) {
        this.relatedStore = relatedStore;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Integer getProductSize() {
        return productSize;
    }

    public void setProductSize(Integer productSize) {
        this.productSize = productSize;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}