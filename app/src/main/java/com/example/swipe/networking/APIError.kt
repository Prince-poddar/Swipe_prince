package com.example.swipe.networking

import com.google.gson.annotations.SerializedName

data class APIError(
    @SerializedName("code") var code: Int? = null,
    @SerializedName("message") var message: String? = null,
)