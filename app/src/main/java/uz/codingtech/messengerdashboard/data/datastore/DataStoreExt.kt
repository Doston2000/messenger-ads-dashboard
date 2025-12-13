package uz.codingtech.messengerdashboard.data.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.authDataStore by preferencesDataStore(
    name = "messenger_ads_datastore"
)