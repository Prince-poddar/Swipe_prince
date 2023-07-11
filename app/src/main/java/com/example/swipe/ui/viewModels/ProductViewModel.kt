package com.example.swipe.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipe.networking.Resource
import com.example.swipe.networking.SingleLiveEvent
import com.example.swipe.networking.response.ProductResponse
import com.example.swipe.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel (private val repository: UsersRepository) : ViewModel() {

    private val _productResponse = SingleLiveEvent<Resource<List<ProductResponse>>>()
    val productResponse: SingleLiveEvent<Resource<List<ProductResponse>>>
        get() = _productResponse


    fun getProductList() =
        viewModelScope.launch {
            _productResponse.postValue(Resource.loading(null))

            kotlin.runCatching {
                withContext(Dispatchers.IO){
                    repository.getProducts()
                }
            }.onSuccess {
               // if (it.isSuccessful) {
                    _productResponse.postValue(Resource.success(it))
//                } else {
//                    val error =
//                        it.errorBody()?.let { errorBody -> ErrorUtility.parseError(errorBody) }
//                    _productResponse.postValue(Resource.error(error!!, null))
//                }

            }.onFailure {
                _productResponse.postValue(Resource.error(it.message.toString(), null))

            }

        }


}