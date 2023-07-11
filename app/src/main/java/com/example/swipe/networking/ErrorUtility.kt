package com.example.swipe.networking

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import java.io.IOException

object ErrorUtility {

    fun  parseError(errorBody: ResponseBody) : String {
        val gson = Gson()
        val errorResponse: APIError?
        val type = object : TypeToken<APIError>() {}.type
        return try {
            errorResponse = gson.fromJson(errorBody.charStream(), type)
            errorResponse!!.message.toString()
        }catch (e : IOException){
            "Something went wrong, Please try again"
        }
    }
}