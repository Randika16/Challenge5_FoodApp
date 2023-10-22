package com.example.challenge2_foodapp.data.repository

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenge2_foodapp.di.CartInjector
import com.example.challenge2_foodapp.ui.activity.confirmation.ConfirmationOrderViewModel
import com.example.challenge2_foodapp.ui.fragment.cart.CartViewModel

class CartViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(CartInjector.provideRepository(context)) as T
        } else if (modelClass.isAssignableFrom(ConfirmationOrderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConfirmationOrderViewModel(CartInjector.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}