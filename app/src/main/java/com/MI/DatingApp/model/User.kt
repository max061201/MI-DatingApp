package com.MI.DatingApp.model

import java.text.SimpleDateFormat
import java.util.*

data class User(
    val id: String ="",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var confirmedPassword: String = "",
    var yearOfBirth: String = "",
    var gender: String = "",
    var genderLookingFor: String = "",
    var imageUrls: MutableList<String>? = mutableListOf(),
    var description: String = "",
    var interest: MutableList<String> = mutableListOf(),
    var likes: MutableList<String> = mutableListOf(),      // IDs der Nutzer, die geliked wurden
    var dislikes: MutableList<String> = mutableListOf(),    // IDs der Nutzer, die disliked wurden
    val receivedLikes: MutableList<String> = mutableListOf() // eingehende Likes

){
    fun emptyFields(): List<String> {
        val emptyFields = mutableListOf<String>()
        if (name.isEmpty()) {
            emptyFields.add("name")
        }
        if (email.isEmpty() || !checkEmailPattern(email)) {
            emptyFields.add("email")
        }
        if (password.isEmpty()) {
            emptyFields.add("password")
        }
        if (confirmedPassword.isEmpty()) {
            emptyFields.add("confirmedPassword")
        }
        return emptyFields
    }

    fun matchPassword(): Boolean {
        return password == confirmedPassword && password.length >= 6
    }

    private fun checkEmailPattern(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailPattern)
    }
}

fun User.calculateAge(): Int {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val dob = sdf.parse(this.yearOfBirth) ?: return 0 // Falls das Parsing fehlschlägt, 0 zurückgeben

    val today = Calendar.getInstance()

    val dobCalendar = Calendar.getInstance()
    dobCalendar.time = dob

    var age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)
    if (today.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
        age--
    }
    return age
}


