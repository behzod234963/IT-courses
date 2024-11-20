package com.mr.anonym.it_courses.di.module

import android.content.Context
import com.mr.anonym.data.local.dataStore.DataStoreInstance
import com.mr.anonym.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStoreInstance =
        DataStoreInstance(context)

    @Provides
    @Singleton
    fun provideRetrofit() =
        Retrofit
            .Builder()
            .baseUrl("https://stepik.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}