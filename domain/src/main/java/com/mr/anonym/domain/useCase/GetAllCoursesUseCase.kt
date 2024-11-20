package com.mr.anonym.domain.useCase

import android.util.Log
import com.mr.anonym.domain.model.CoursesModel
import com.mr.anonym.domain.repository.CoursesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAllCoursesUseCase(private val repository: CoursesRepository) {

    fun execute (): List<CoursesModel> {
        var courses :List<CoursesModel> = emptyList()
        try {
            CoroutineScope(Dispatchers.IO).launch{
                repository.getAllCourses().enqueue(object :Callback<List<CoursesModel>>{
                    override fun onResponse(
                        p0: Call<List<CoursesModel>?>,
                        response: Response<List<CoursesModel>?>
                    ) {
                        if (response.isSuccessful){
                            courses = response.body()!!
                        }
                    }

                    override fun onFailure(
                        p0: Call<List<CoursesModel>?>,
                        p1: Throwable
                    ) {
                        Log.d("NetworkLogging", "onFailure: Data not found")
                    }
                })
            }
        }catch (_: Exception){}
        return courses
    }
}