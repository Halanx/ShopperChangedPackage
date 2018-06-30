package com.shopperapphalanx.Interfaces;


import com.shopperapphalanx.POJO.DocsInfo;
import com.shopperapphalanx.POJO.ShopperInfo;
import com.shopperapphalanx.POJO.earningpagination;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by samarthgupta on 05/04/17.
 */

public interface DataInterface {

    //Post shopper on server
    @POST("/users/")
    Call<ShopperInfo> putDataOnServer(@Body ShopperInfo userData, @Header("Authorization") String token);


    @GET("/NewShopper/")
    Call<List<ShopperInfo>> getDataFromServer();

    //Posting documents
    @POST("/shoppers/documents/")
    Call<DocsInfo> postShopperDocuments(@Body DocsInfo documents,@Header("Authorization") String token);

    //Posting documents
    @GET("shoppers/batches/delivered/?")
    Call<earningpagination> geteraningdata(@Header("Authorization") String token, @Query("page") String page);

    //Posting documents
    @GET("shoppers/batches/current/")
    Call<earningpagination> getcurrentbatch(@Header("Authorization") String token);




}