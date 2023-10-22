package com.example.challenge2_foodapp.data.sharedpreferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RecyclerViewViewModelFactory (private val pref: RecyclerViewSettings) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecyclerViewViewModel::class.java)) {
            return RecyclerViewViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}
