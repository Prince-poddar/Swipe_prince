package com.example.swipe.repository

import com.example.swipe.networking.UserServices
import okhttp3.MultipartBody

class UsersRepository(private val userServices: UserServices) {
    suspend fun getProducts() = userServices.productList()
    suspend fun addProducts(
        productName: MultipartBody.Part?,
        productType: MultipartBody.Part?,
        sellingPrice: MultipartBody.Part?,
        tax: MultipartBody.Part?
    )=userServices.addProduct(productName,productType,sellingPrice,tax)
}