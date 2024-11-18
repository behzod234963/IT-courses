package com.mr.anonym.data.local.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("dataStore")
class DataStoreInstance (private val context: Context){


}