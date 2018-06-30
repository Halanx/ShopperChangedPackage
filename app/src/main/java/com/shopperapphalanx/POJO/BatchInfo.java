package com.shopperapphalanx.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samarthgupta on 19/07/17.
 */

public class BatchInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("batch_items")
    @Expose
    private List<BatchItem> batchItems = null;
    @SerializedName("earnings")
    @Expose
    private Double earnings;
    @SerializedName("PermanentShopper")
    @Expose
    private long permanentShopper;
    @SerializedName("PermanentAvailable")
    @Expose
    private Boolean permanentAvailable;
    @SerializedName("TemporaryShopper")
    @Expose
    private long temporaryShopper;
    @SerializedName("TemporaryAvailable")
    @Expose
    private Boolean temporaryAvailable;
    @SerializedName("centroid_latitude")
    @Expose
    private Double centroidLatitude;
    @SerializedName("centroid_longitude")
    @Expose
    private Double centroidLongitude;
    @SerializedName("IsDelivered")
    @Expose
    private Boolean isDelivered;
    @SerializedName("Size")
    @Expose
    private Integer size;
    @SerializedName("ShopperId")
    @Expose
    private Integer shopperId;

    public Integer getId() {
        return id;
    }

    public List<BatchItem> getBatchItems() {
        return batchItems;
    }

    public Double getEarnings() {
        return earnings;
    }

    public long getPermanentShopper() {
        return permanentShopper;
    }

    public Boolean getPermanentAvailable() {
        return permanentAvailable;
    }

    public long getTemporaryShopper() {
        return temporaryShopper;
    }

    public Boolean getTemporaryAvailable() {
        return temporaryAvailable;
    }

    public Double getCentroidLatitude() {
        return centroidLatitude;
    }

    public Double getCentroidLongitude() {
        return centroidLongitude;
    }

    public Boolean getDelivered() {
        return isDelivered;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getShopperId() {
        return shopperId;
    }
}
