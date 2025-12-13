package uz.codingtech.messengerdashboard.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.codingtech.messengerdashboard.domain.models.AuthData
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val TOKEN = stringPreferencesKey("token")
        private val USERNAME = stringPreferencesKey("username")
    }

    private val dataStore = context.authDataStore

    suspend fun saveAuthData(authData: AuthData) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = authData.accessToken
            prefs[REFRESH_TOKEN] = authData.refreshToken
            prefs[TOKEN] = authData.token
            prefs[USERNAME] = authData.username
        }
    }

    val authData: Flow<AuthData?> = dataStore.data.map { prefs ->
        AuthData(
            accessToken = prefs[ACCESS_TOKEN] ?: "",
            refreshToken = prefs[REFRESH_TOKEN] ?: "",
            token = prefs[TOKEN] ?: "",
            username = prefs[USERNAME] ?: ""
        )
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}

