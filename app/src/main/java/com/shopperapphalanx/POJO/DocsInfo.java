
package com.shopperapphalanx.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DocsInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ShopperPhoneNo")
    @Expose
    private long shopperPhoneNo;
    @SerializedName("AadharImage")
    @Expose
    private String aadharImage;
    @SerializedName("LicenseImage")
    @Expose
    private String licenseImage;
    @SerializedName("VehicleRCImage")
    @Expose
    private String vehicleRCImage;
    @SerializedName("AadharURL")
    @Expose
    private String aadharURL;
    @SerializedName("LicenseURL")
    @Expose
    private String licenseURL;
    @SerializedName("VehicleRCURL")
    @Expose
    private String vehicleRCURL;

    public DocsInfo(){

    }

    public DocsInfo(long shopperPhoneNo, String aadharImage, String licenseImage, String vehicleRCImage) {
        this.shopperPhoneNo = shopperPhoneNo;
        this.aadharImage =  aadharImage;
        this.licenseImage = licenseImage;
        this.vehicleRCImage = vehicleRCImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getShopperPhoneNo() {
        return shopperPhoneNo;
    }

    public void setShopperPhoneNo(long shopperPhoneNo) {
        this.shopperPhoneNo = shopperPhoneNo;
    }

    public String getAadharImage() {
        return aadharImage;
    }

    public void setAadharImage(String aadharImage) {
        this.aadharImage = aadharImage;
    }

    public String getLicenseImage() {
        return licenseImage;
    }

    public void setLicenseImage(String licenseImage) {
        this.licenseImage = licenseImage;
    }

    public Object getVehicleRCImage() {
        return vehicleRCImage;
    }

    public void setVehicleRCImage(String vehicleRCImage) {
        this.vehicleRCImage = vehicleRCImage;
    }

    public String getAadharURL() {
        return aadharURL;
    }

    public void setAadharURL(String aadharURL) {
        this.aadharURL = aadharURL;
    }

    public String getLicenseURL() {
        return licenseURL;
    }

    public void setLicenseURL(String licenseURL) {
        this.licenseURL = licenseURL;
    }

    public String getVehicleRCURL() {
        return vehicleRCURL;
    }

    public void setVehicleRCURL(String vehicleRCURL) {
        this.vehicleRCURL = vehicleRCURL;
    }

}