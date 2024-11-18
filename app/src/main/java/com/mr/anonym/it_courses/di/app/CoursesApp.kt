package com.mr.anonym.it_courses.di.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoursesApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}