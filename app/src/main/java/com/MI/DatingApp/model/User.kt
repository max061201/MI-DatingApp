package com.MI.DatingApp.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val yearOfBirth: String,
    val gender: Gender,
    val lookingFor: LookingFor,
    val description: String,
    val interests: List<Interest>,
    val photos: List<String>
)