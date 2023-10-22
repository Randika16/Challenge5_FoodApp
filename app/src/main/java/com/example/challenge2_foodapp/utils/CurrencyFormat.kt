package com.example.challenge2_foodapp.utils

import android.icu.text.NumberFormat
import android.icu.util.Currency
import kotlin.math.roundToInt

fun Double.toCurrencyFormat():String{
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("IDR")
    return format.format(this.roundToInt())
}

fun Int.toCurrencyFormat():String{
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("IDR")
    return format.format(this)
}

fun Float.toCurrencyFormat():String{
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("IDR")
    return format.format(this)
}