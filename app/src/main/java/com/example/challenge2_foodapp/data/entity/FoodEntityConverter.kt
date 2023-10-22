package com.example.challenge2_foodapp.data.entity

import androidx.room.TypeConverter
import com.google.gson.Gson

class FoodEntityConverter {

    @TypeConverter
    fun fromFoodEntity(foodEntity: FoodEntity?): String {
        return Gson().toJson(foodEntity)
    }

    @TypeConverter
    fun toFoodEntity(foodEntityString: String): FoodEntity? {
        return Gson().fromJson(foodEntityString, FoodEntity::class.java)
    }

}