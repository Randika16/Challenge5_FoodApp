package com.example.challenge2_foodapp.data.sharedpreferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecyclerViewViewModel (private val pref: RecyclerViewSettings) : ViewModel() {

    fun rvAppearance(): LiveData<String> {
        return pref.getRecyclerViewSetting().asLiveData()
    }

    fun setRVAppearance(rvAppearance: String) {
        viewModelScope.launch {
            pref.saveRecyclerViewSetting(rvAppearance)
        }
    }

}
