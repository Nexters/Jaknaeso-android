package com.jaknaeso.app.data.token

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jaknaeso.app.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    corruptionHandler = ReplaceFileCorruptionHandler {
        it.printStackTrace()
        emptyPreferences()
    },
    scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    name = BuildConfig.LIBRARY_PACKAGE_NAME
)

class TokenManagerImpl @Inject constructor(@ApplicationContext context: Context):TokenManager {

    private val dataStore = context.datastore

    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("AUTH_TOKEN")
        private val REMEMBERED_TOKEN_KEY = stringPreferencesKey("REMEMBERED_TOKEN")
    }

    override fun getAuthTokenForHeader(): String {
        val token: String? = runBlocking {
            getAuthToken().first()
        }
        if (token != null) {
            Log.d("TokenManagerImpl", "getAuthTokenForHeader: AuthToken(accessToken):${token}")
        }
        return token ?: ""
    }

    override suspend fun getAuthToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            Log.d("TokenManagerImpl", "getAuthToken: AuthToken(accessToken):${preferences[AUTH_TOKEN_KEY]}")
            preferences[AUTH_TOKEN_KEY]
        }
    }

    override suspend fun getRememberedToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[REMEMBERED_TOKEN_KEY]
        }
    }

    override suspend fun saveAuthToken(token: String) {
        dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    override suspend fun saveRememberedToken(token: String) {
        dataStore.edit { preferences ->
            preferences[REMEMBERED_TOKEN_KEY] = token
        }
    }
}
