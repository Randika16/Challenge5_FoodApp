package com.example.challenge2_foodapp.data.api

data class RequestOrder(
    val username: String,
    val total: Int,
    val orders: List<Order>
)

data class Order(
    val nama: String,
    val qty: Int,
    val catatan: String? = null,
    val harga: Int
)