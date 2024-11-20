package com.mr.anonym.it_courses.di.app

import android.app.Application
import android.widget.Toast
import com.mr.anonym.data.local.dataStore.DataStoreInstance
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class CoursesApp: Application() {

    val dataStoreInstance = DataStoreInstance(this)

    override fun onCreate() {
        super.onCreate()
        VK.addTokenExpiredHandler(tokenTracker)
    }
    private val tokenTracker = object: VKTokenExpiredHandler {
        override fun onTokenExpired() {
            CoroutineScope(Dispatchers.IO).launch{
                dataStoreInstance.setIsAuthorized(false)
            }
            Toast.makeText(this@CoursesApp, "Your token is expired", Toast.LENGTH_SHORT).show()
        }
    }
}