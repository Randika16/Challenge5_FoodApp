package com.example.challenge2_foodapp.di

import android.content.Context
import com.example.challenge2_foodapp.data.room.AppDatabase
import com.example.challenge2_foodapp.data.CartDataSource
import com.example.challenge2_foodapp.data.CartDatabaseDataSource
import com.example.challenge2_foodapp.data.repository.CartRepository

object CartInjector {
    fun provideRepository(context: Context): CartRepository {
        val database = AppDatabase.getDatabase(context)
        val cartDao = database.CartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        return CartRepository(cartDataSource)
    }
}