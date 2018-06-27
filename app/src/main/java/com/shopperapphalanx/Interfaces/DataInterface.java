package com.shopperapphalanx.Interfaces;


import com.shopperapphalanx.POJO.DocsInfo;
import com.shopperapphalanx.POJO.ShopperInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by samarthgupta on 05/04/17.
 */

public interface DataInterface {

    //Post shopper on server
    @POST("/users/")
    Call<ShopperInfo> putDataOnServer(@Body ShopperInfo userData, @Header("Authenticatication") String token);


    @GET("/NewShopper/")
    Call<List<ShopperInfo>> getDataFromServer();

    //Posting documents
    @POST("/shoppers/documents/")
    Call<DocsInfo> postShopperDocuments(@Body DocsInfo documents,@Header("Authenticatication") String token);




}