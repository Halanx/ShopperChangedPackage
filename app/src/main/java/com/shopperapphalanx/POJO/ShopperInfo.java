
package com.shopperapphalanx.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopperInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("PhoneNo")
    @Expose
    private long phoneNo;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("City")
    @Expose
    private String city;

    @SerializedName("DisplayPictureURL")
    @Expose
    private String displayPictureURL;
    @SerializedName("AccessToken")
    @Expose
    private String accessToken;
    @SerializedName("GcmId")
    @Expose
    private String gcmid;
    @SerializedName("IdNumber")
    @Expose
    private String idNumber;
    @SerializedName("IdType")
    @Expose
    private String idType;
    @SerializedName("Vehicle")
    @Expose
    private String vehicle;
    @SerializedName("VehicleSpaceAvailable")
    @Expose
    private Integer vehicleSpaceAvailable;
    @SerializedName("AvailableDate")
    @Expose
    private String availableDate;
    @SerializedName("AvailableFrom")
    @Expose
    private String availableFrom;
    @SerializedName("AvailableTo")
    @Expose
    private String availableTo;
    @SerializedName("IsOnline")
    @Expose
    private Boolean isOnline;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("BankAccountNumber")
    @Expose
    private String bankAccountNumber;
    @SerializedName("BankAccountType")
    @Expose
    private String bankAccountType;
    @SerializedName("BankName")
    @Expose
    private String bankName;
    @SerializedName("BankBranch")
    @Expose
    private String bankBranch;
    @SerializedName("BankBranchAddress")
    @Expose
    private String bankBranchAddress;
    @SerializedName("IFSCCode")
    @Expose
    private String iFSCCode;
    @SerializedName("AvgRating")
    @Expose
    private Double avgRating;
    @SerializedName("n")
    @Expose
    private Integer n;
    @SerializedName("A")
    @Expose
    private Double a;
    @SerializedName("B")
    @Expose
    private Double b;
    @SerializedName("C")
    @Expose
    private Double c;
    @SerializedName("D")
    @Expose
    private Double d;
    @SerializedName("E")
    @Expose
    private Double e;
    @SerializedName("Verified")
    @Expose
    private Boolean verified;

    public ShopperInfo(String firstName, String lastName, long phoneNo, String password, String emailId, String city, String firebaseid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
        this.password = password;
        this.emailId = emailId;
        this.city = city;
        gcmid = firebaseid;
    }

    public void setGcmid(String gcmid) {
        this.gcmid = gcmid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDisplayPictureURL() {
        return displayPictureURL;
    }

    public void setDisplayPictureURL(String displayPictureURL) {
        this.displayPictureURL = displayPictureURL;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getGcmid() {
        return gcmid;
    }

    public void setGcmId(String gcmid) {
        this.gcmid = gcmid;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }



    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getVehicleSpaceAvailable() {
        return vehicleSpaceAvailable;
    }

    public void setVehicleSpaceAvailable(Integer vehicleSpaceAvailable) {
        this.vehicleSpaceAvailable = vehicleSpaceAvailable;
    }

    public String getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(String availableDate) {
        this.availableDate = availableDate;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(String availableTo) {
        this.availableTo = availableTo;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankBranchAddress() {
        return bankBranchAddress;
    }

    public void setBankBranchAddress(String bankBranchAddress) {
        this.bankBranchAddress = bankBranchAddress;
    }

    public String getiFSCCode() {
        return iFSCCode;
    }

    public void setiFSCCode(String iFSCCode) {
        this.iFSCCode = iFSCCode;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Double getA() {
        return a;
    }

    public void setA(Double a) {
        this.a = a;
    }

    public Double getB() {
        return b;
    }

    public void setB(Double b) {
        this.b = b;
    }

    public Double getC() {
        return c;
    }

    public void setC(Double c) {
        this.c = c;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public Double getE() {
        return e;
    }

    public void setE(Double e) {
        this.e = e;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}