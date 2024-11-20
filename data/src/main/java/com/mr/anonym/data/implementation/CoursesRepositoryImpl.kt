package com.mr.anonym.data.implementation

import com.mr.anonym.data.remote.ApiService
import com.mr.anonym.domain.model.CoursesModel
import com.mr.anonym.domain.repository.CoursesRepository
import retrofit2.Call

class CoursesRepositoryImpl(private val apiService: ApiService): CoursesRepository {

    override fun getAllCourses(): Call<List<CoursesModel>> =
        apiService.getAllCourses()
}