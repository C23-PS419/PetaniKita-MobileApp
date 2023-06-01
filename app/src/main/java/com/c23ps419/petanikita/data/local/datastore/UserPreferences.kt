package com.c23ps419.petanikita.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){

    private object PreferencesKeys {
        val USER_TOKEN = stringPreferencesKey("user_token")
        val USER_SESSION = booleanPreferencesKey("user_session")
    }

    fun getUserSession(): Flow<Boolean> {
        return dataStore.data.map {preferences ->
            preferences[PreferencesKeys.USER_SESSION] ?: false
        }
    }

    fun getUserToken(): Flow<String> {
        return dataStore.data.map {preferences ->
            preferences[PreferencesKeys.USER_TOKEN] ?: ""
        }
    }

    suspend fun saveUserLoginData(token: String, session: Boolean){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_TOKEN] = token
            preferences[PreferencesKeys.USER_SESSION] = session
        }
    }

    suspend fun onUserLogout(){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_TOKEN] = ""
            preferences[PreferencesKeys.USER_SESSION] = false
        }
    }

    companion object{
        @Volatile
        private var userPreferences: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return userPreferences ?: synchronized(this){
                val instance = UserPreferences(dataStore)
                userPreferences = instance
                instance
            }
        }
    }
}