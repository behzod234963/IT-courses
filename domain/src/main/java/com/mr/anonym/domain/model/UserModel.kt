package com.mr.anonym.domain.model

data class UserModel(

    val uid: String,
    val email: String,
    val password: String,
    val dateOfBirth: String,
    val firstName: String,
    val lastName: String,
    val token: String? = null,
)
