package com.example.swipe.networking

import com.example.swipe.networking.response.AddProductDetails
import com.example.swipe.networking.response.ProductResponse
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UserServices {

    @GET("get")
    suspend fun productList(): List<ProductResponse>


    @Multipart
    @POST("add")
    suspend fun addProduct(
        @Part product_name: MultipartBody.Part?,
        @Part product_type: MultipartBody.Part?,
        @Part price: MultipartBody.Part?,
        @Part tax: MultipartBody.Part?,
       // @Part files: MultipartBody.Part?,
    ): retrofit2.Response<AddProductDetails>
}