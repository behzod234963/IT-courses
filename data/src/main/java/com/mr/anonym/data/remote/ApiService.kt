package com.mr.anonym.data.remote

import com.mr.anonym.domain.model.CoursesModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/api/course-lists")
    fun getAllCourses() : Call<List<CoursesModel>>
}