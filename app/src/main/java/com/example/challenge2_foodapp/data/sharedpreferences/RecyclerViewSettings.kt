package com.example.challenge2_foodapp.data.sharedpreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RecyclerViewSettings private constructor(private val dataStore: DataStore<Preferences>) {

    private val RECYCLERVIEW_KEY = stringPreferencesKey("recyclerview_settings")

    fun getRecyclerViewSetting(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[RECYCLERVIEW_KEY] ?: "list"
        }
    }

    suspend fun saveRecyclerViewSetting(rvAppearance: String) {
        dataStore.edit { preferences ->
            preferences[RECYCLERVIEW_KEY] = rvAppearance
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: RecyclerViewSettings? = null

        fun getInstance(dataStore: DataStore<Preferences>): RecyclerViewSettings {
            return INSTANCE ?: synchronized(this) {
                val instance = RecyclerViewSettings(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}