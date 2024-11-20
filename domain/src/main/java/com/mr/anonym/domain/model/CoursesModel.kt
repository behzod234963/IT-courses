package com.mr.anonym.domain.model

data class CoursesModel(

	val id: Int ,
	val courses: List<Int>,
	val description: String,
	val language: String,
	val position: Int,
	val title: String,
	val socialImageUrl: String,
	val platform: Int
)