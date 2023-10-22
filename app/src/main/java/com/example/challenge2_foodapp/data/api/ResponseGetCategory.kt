package com.example.challenge2_foodapp.data.api

data class ResponseGetCategory(
    val status: String,
    val code: Int,
    val message: String,
    val data: List<Category>
)

data class Category(
    val image_url: String,
    val nama: String,
)
