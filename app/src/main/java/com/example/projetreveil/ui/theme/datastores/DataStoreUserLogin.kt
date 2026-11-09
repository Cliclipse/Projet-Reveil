package com.example.projetreveil.ui.theme.datastores

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userLoginDataStore by preferencesDataStore(name = "user-login")

class DataStoreUserLogin(private val context : Context){

    private val LAST_USERNAME_KEY = stringPreferencesKey("last_username")

    private val IS_OFFLINE_KEY = booleanPreferencesKey("is_offline")


    val lastUsername: Flow<String> = context.userLoginDataStore.data.map {
            preferences ->
        preferences[LAST_USERNAME_KEY] ?: ""
    }

    val isOfflineMode: Flow<Boolean> = context.userLoginDataStore.data.map {
            preferences ->
        preferences[IS_OFFLINE_KEY] ?: true
    }

    suspend fun defineLastUsername(lastUsername: String){
        context.userLoginDataStore.edit {
            preferences -> preferences[LAST_USERNAME_KEY] = lastUsername
        }
    }

    suspend fun defineIsOfflineMode(isOffline: Boolean){
        context.userLoginDataStore.edit {
            preferences -> preferences[IS_OFFLINE_KEY] = isOffline
        }
    }
}