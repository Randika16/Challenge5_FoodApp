package com.example.challenge2_foodapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("cart_entity")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_id")
    val id: Int = 0,

    @ColumnInfo(name = "cart_food_entity")
    val foodItem: FoodEntity,

    @ColumnInfo(name = "cart_food_quantity")
    val foodQuantity: Int = 0,

    @ColumnInfo(name = "cart_food_note")
    val foodNote: String? = null
)