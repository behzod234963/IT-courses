package com.mr.anonym.domain.repository

import com.mr.anonym.domain.model.CoursesModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Call

interface CoursesRepository {

    fun getAllCourses() : Call<List<CoursesModel>>
}