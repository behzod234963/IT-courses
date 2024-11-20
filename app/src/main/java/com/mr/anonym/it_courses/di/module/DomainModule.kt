package com.mr.anonym.it_courses.di.module

import com.mr.anonym.data.implementation.CoursesRepositoryImpl
import com.mr.anonym.data.remote.ApiService
import com.mr.anonym.domain.repository.CoursesRepository
import com.mr.anonym.domain.useCase.CoursesUseCases
import com.mr.anonym.domain.useCase.GetAllCoursesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideCoursesRepository(apiService: ApiService): CoursesRepository = CoursesRepositoryImpl(apiService)

    @Provides
    @Singleton
    fun provideCoursesUseCases(repository: CoursesRepository) = CoursesUseCases(
        getAllCoursesUseCase = GetAllCoursesUseCase(repository)
    )
}