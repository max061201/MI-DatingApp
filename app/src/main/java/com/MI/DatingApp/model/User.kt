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
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val birthDate = dateFormat.parse(this.yearOfBirth) ?: return 0
    val today = Calendar.getInstance().time
    val age = today.year - birthDate.year
    if (today.before(birthDate)) age - 1
    return age
}

