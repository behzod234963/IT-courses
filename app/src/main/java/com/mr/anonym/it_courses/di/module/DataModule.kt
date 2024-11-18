package com.mr.anonym.it_courses.di.module

import android.content.Context
import com.mr.anonym.data.local.dataStore.DataStoreInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context):DataStoreInstance = DataStoreInstance(context)
}