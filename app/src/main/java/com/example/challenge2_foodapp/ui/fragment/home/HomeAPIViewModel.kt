package com.example.challenge2_foodapp.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge2_foodapp.data.api.APIConfig
import com.example.challenge2_foodapp.data.api.Category
import com.example.challenge2_foodapp.data.api.Food
import com.example.challenge2_foodapp.data.api.FoodDummy
import com.example.challenge2_foodapp.data.api.ResponseGetCategory
import com.example.challenge2_foodapp.data.api.ResponseGetFood
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeAPIViewModel : ViewModel() {

    private val _isLoadingFood = MutableLiveData<Boolean>()
    val isLoadingLogin: LiveData<Boolean> = _isLoadingFood

    var food: List<Food> = listOf()

    val foodDummy = MutableLiveData<List<Food>>()

    private val _category = MutableLiveData<List<Category>>()
    val category: LiveData<List<Category>> = _category

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun getFoods(category: String? = null) {
        _isLoadingFood.value = true
        val api = APIConfig.getApiService().getFoods(category)
        api.enqueue(object : Callback<ResponseGetFood> {
            override fun onResponse(
                call: Call<ResponseGetFood>,
                response: Response<ResponseGetFood>
            ) {
                _isLoadingFood.value = false
                val responseBody = response.body()

                if (response.isSuccessful) {
                    _message.value = responseBody?.message
                    if (responseBody?.data != null) {
                        food = responseBody.data
                        foodDummy.value = food
                    }

                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseGetFood>, t: Throwable) {
                _isLoadingFood.value = false
                _message.value = t.message
            }

        })
    }

    fun getCategories() {
        _isLoadingFood.value = true
        val api = APIConfig.getApiService().getCategories()
        api.enqueue(object : Callback<ResponseGetCategory> {
            override fun onResponse(
                call: Call<ResponseGetCategory>,
                response: Response<ResponseGetCategory>
            ) {
                _isLoadingFood.value = false
                val responseBody = response.body()

                if (response.isSuccessful) {
                    _message.value = responseBody?.message
                    _category.value = responseBody?.data
                } else {
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<ResponseGetCategory>, t: Throwable) {
                _isLoadingFood.value = false
                _message.value = t.message
            }

        })
    }
}