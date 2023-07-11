package com.example.swipe.networking.response

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("image") var image: String? = null,
    @SerializedName("price") var price: Double? = null,
    @SerializedName("product_name") var productName: String? = null,
    @SerializedName("product_type") var productType: String? = null,
    @SerializedName("tax") var tax: Double? = null
)
