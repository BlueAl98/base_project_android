package com.example.baseproyect.data.dataStore



import javax.inject.Inject
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import androidx.datastore.preferences.core.Preferences


// TokenLocalDataSource.kt
class TokenLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_KEY]
    }

    suspend fun deleteToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY) // Elimina la clave del token
        }
    }

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token_key")
    }
}