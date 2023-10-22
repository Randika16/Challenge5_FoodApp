package com.example.challenge2_foodapp.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {

    @GET("listmenu")
    fun getFoods(
        @Query("c") category: String? = null
    ): Call<ResponseGetFood>

    @GET("foodapp")
    fun getFoodsDummy(): Call<List<FoodDummy>>

    @GET("category")
    fun getCategories(): Call<ResponseGetCategory>

    @POST("order")
    fun order(
        @Body body: RequestOrder
    ): Call<ResponseOrder>
}