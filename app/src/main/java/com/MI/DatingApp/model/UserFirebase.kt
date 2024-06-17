package com.MI.DatingApp.model

data class UserFirebase(
    var id:Long,
    val name: String,
    val email: String,
    val yearOfBirth: String,
    val gender: String,
    val lookingFor: String,
    val description: String,
    val interests: MutableSet<String>,
    val photos: List<Int>
)