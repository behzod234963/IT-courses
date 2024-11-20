package com.mr.anonym.data.local.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("dataStore")
class DataStoreInstance (private val context: Context){

    suspend fun firebaseUser(user:FirebaseUser){
        val key = stringPreferencesKey("user")
        context.dataStore.edit {input ->
            input[key] = user.toString()
        }
    }
    fun getFirebaseUser(): Flow<String> {
        val key = stringPreferencesKey("user")
        return context.dataStore.data.map { input ->
            input[key] ?: ""
        }
    }
    suspend fun isFirstTime(isFirstTime: Boolean){
        val key = booleanPreferencesKey("isFirstTime")
        context.dataStore.edit {status ->
            status[key] = isFirstTime
        }
    }
    fun getIsFirstTime(): Flow<Boolean>{
        val key = booleanPreferencesKey("isFirstTime")
        return context.dataStore.data.map {status ->
            status[key] ?: false
        }
    }

    suspend fun setIsAuthorized(isAuthorized: Boolean){

        val key = booleanPreferencesKey("isAuthorized")
        context.dataStore.edit {status ->
            status[key] = isAuthorized
        }
    }
    fun getIsAuthorized(): Flow<Boolean>{

        val key = booleanPreferencesKey("isAuthorized")
        return context.dataStore.data.map {isAuthorized ->
            isAuthorized[key]!!
        }
    }

    suspend fun saveEmail(email:String) {
        val key = stringPreferencesKey("email")
        context.dataStore.edit { input ->
            input[key] = email
        }
    }
    fun getEmail(): Flow<String> {
        val key = stringPreferencesKey("email")
        return context.dataStore.data.map { input ->
            input[key] ?: ""
        }
    }
    suspend fun savePhone(phone:String) {
        val key = stringPreferencesKey("phone")
        context.dataStore.edit { input ->
            input[key] = phone
        }
    }
    fun getPhone(): Flow<String> {
        val key = stringPreferencesKey("phone")
        return context.dataStore.data.map { input ->
            input[key] ?: ""
        }
    }
    suspend fun saveToken(token:String){
        val key = stringPreferencesKey("token")
        context.dataStore.edit {input ->
            input[key] = token
        }
    }
    fun getToken(): Flow<String>{
        val key = stringPreferencesKey("token")
        return context.dataStore.data.map {input ->
            input[key] ?: ""
        }
    }
}