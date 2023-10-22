package com.example.challenge2_foodapp.data.api

import android.os.Parcelable
import android.security.identity.AccessControlProfileId
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class ResponseGetFood(
    val status: String,
    val code: Int,
    val message: String,
    val data: List<Food>
)

data class Food(
    val image_url: String,
    val nama: String,
    val harga_format: String,
    val harga: Int,
    val detail: String,
    val alamat_resto: String,
) : Serializable

@Parcelize
data class FoodDummy(
    val image_url: String,
    val nama: String,
    val harga_format: String,
    val harga: String,
    val detail: String,
    val alamat_resto: String,
    val id: String
) : Parcelable
