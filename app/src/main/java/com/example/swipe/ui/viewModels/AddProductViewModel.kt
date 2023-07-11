package com.example.swipe.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipe.networking.ErrorUtility
import com.example.swipe.networking.Resource
import com.example.swipe.networking.SingleLiveEvent
import com.example.swipe.networking.response.AddProductDetails
import com.example.swipe.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddProductViewModel(private val repository: UsersRepository) : ViewModel() {

    private val _addProductResponse = SingleLiveEvent<Resource<AddProductDetails>>()
    val addProductResponse: SingleLiveEvent<Resource<AddProductDetails>>
        get() = _addProductResponse

    fun addProduct(
        name: String,
        productType: String,
        sellingPrice: String,
        productTax: String
    ) = viewModelScope.launch {
        _addProductResponse.postValue(Resource.loading(null))
        try {
            var productName: MultipartBody.Part? = null
            var product_Type: MultipartBody.Part? = null
            var price: MultipartBody.Part? = null
            var tax: MultipartBody.Part? = null

            if (name != null && name != "") {

                val requestFileText = RequestBody.create(
                    MediaType.parse("text/plain"),
                    name
                )
                productName =
                    requestFileText.let {
                        MultipartBody.Part.createFormData(
                            "product_name",
                            name
                        )
                    }
            }

            if (productType != null && productType != "") {

                val requestFileText = RequestBody.create(
                    MediaType.parse("text/plain"),
                    productType
                )
                product_Type =
                    requestFileText.let {
                        MultipartBody.Part.createFormData(
                            "product_type",
                            productType
                        )
                    }
            }

            if (sellingPrice != null && sellingPrice != "") {

                val requestFileText = RequestBody.create(
                    MediaType.parse("text/plain"),
                    sellingPrice
                )
                price =
                    requestFileText.let {
                        MultipartBody.Part.createFormData(
                            "price",
                            sellingPrice
                        )
                    }
            }

            if (productTax != null && productTax != "") {

                val requestFileText = RequestBody.create(
                    MediaType.parse("text/plain"),
                    productTax
                )
                tax =
                    requestFileText.let {
                        MultipartBody.Part.createFormData(
                            "tax",
                            productTax
                        )
                    }
            }



            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    repository.addProducts(productName,product_Type,price,tax)
                }
            }.onSuccess {
                if (it.isSuccessful) {
                    _addProductResponse.postValue(Resource.success(it.body()))
                } else {
                    val error =
                        it.errorBody()?.let { errorBody -> ErrorUtility.parseError(errorBody) }
                    _addProductResponse.postValue(Resource.error(error!!, null))
                }
            }.onFailure {
                _addProductResponse.postValue(Resource.error(it.message.toString(), null))
            }
        } catch (e: Exception) {
            _addProductResponse.postValue(
                Resource.error(
                    "Something went wrong while uploading product",
                    null
                )
            )
        }
    }




}