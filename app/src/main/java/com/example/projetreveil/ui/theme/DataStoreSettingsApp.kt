package com.example.projetreveil.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class DataStoreSettingsApp(private val context : Context) {
    private val NIGHT_KEY = booleanPreferencesKey("night_mode")

    val nightMode: Flow<Boolean> = context.dataStore.data.map {
        preferences ->
            preferences[NIGHT_KEY] ?: false
    }

    suspend fun defineNightMode(night: Boolean){
        context.dataStore.edit {
            preferences -> preferences[NIGHT_KEY] = night
        }
    }

}