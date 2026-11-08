package com.example.projetreveil.ui.theme.datastores

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userLoginDataStore by preferencesDataStore(name = "user-login")

class DataStoreUserLogin(private val context : Context){

    private val LAST_USERNAME_KEY = stringPreferencesKey("last_username")

    val lastUsername: Flow<String> = context.userLoginDataStore.data.map {
            preferences ->
        preferences[LAST_USERNAME_KEY] ?: ""
    }

    suspend fun defineLastUsername(lastUsername: String){
        context.userLoginDataStore.edit {
                preferences -> preferences[LAST_USERNAME_KEY] = lastUsername
        }
    }
}