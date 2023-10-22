package com.example.challenge2_foodapp.ui.activity.confirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.challenge2_foodapp.data.api.APIConfig
import com.example.challenge2_foodapp.data.api.RequestOrder
import com.example.challenge2_foodapp.data.api.ResponseOrder
import com.example.challenge2_foodapp.data.entity.CartEntity
import com.example.challenge2_foodapp.data.entity.FoodEntity
import com.example.challenge2_foodapp.data.repository.CartRepository
import kotlinx.coroutines.flow.map
import retrofit2.Call
import retrofit2.Callback

class ConfirmationOrderViewModel(private val cartRepository: CartRepository) : ViewModel() {


    private val _message = MutableLiveData<String>()
    val message: MutableLiveData<String> = _message

    val cart: LiveData<List<CartEntity>>
        get() = cartRepository.getAllCartItem().map {
            it.map { cartEntity ->
                CartEntity(
                    cartEntity.id,
                    FoodEntity(
                        cartEntity.foodItem.name,
                        cartEntity.foodItem.price,
                        cartEntity.foodItem.priceFormat,
                        cartEntity.foodItem.description,
                        cartEntity.foodItem.location,
                        cartEntity.foodItem.image
                    ),
                    cartEntity.foodQuantity,
                    cartEntity.foodNote
                )
            }
        }.asLiveData()

    fun addCartToServer(requestOrder: RequestOrder) {

        val api = APIConfig.getApiService().order(requestOrder)
        api.enqueue(object : Callback<ResponseOrder> {
            override fun onResponse(
                call: Call<ResponseOrder>,
                response: retrofit2.Response<ResponseOrder>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    if (responseBody?.status == "success") {
                        _message.value = responseBody.message
                    } else {
                        _message.value = responseBody?.message
                    }
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseOrder>, t: Throwable) {
                _message.value = t.message
            }

        })

    }

}