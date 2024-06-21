package com.MI.DatingApp.model

import android.content.Context
import android.content.SharedPreferences

object CurrentUser {
    private var user: User? = null
    private const val PREFERENCES_NAME = "MyAppPreferences"
    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun setUser(newUser: User) {
        user = newUser
        saveUserToPreferences(newUser)
    }

    fun getUser(): User? {
        if (user == null) {
            user = loadUserFromPreferences()
        }
        return user
    }

    fun clearUser() {
        user = null
        clearPreferences()
    }

    private fun saveUserToPreferences(user: User) {
        val editor = sharedPreferences.edit()
        editor.putString("userId", user.id)
        editor.putString("userName", user.name)
        editor.putString("userEmail", user.email)
        editor.putString("userPassword", user.password)
        editor.putString("userConfirmedPassword", user.confirmedPassword)
        editor.putString("userYearOfBirth", user.yearOfBirth)
        editor.putString("userGender", user.gender)
        editor.putStringSet("userImageUrls", user.imageUrls?.toSet())
        editor.putString("userGenderLookingFor", user.genderLookingFor)
        editor.putString("userDescription", user.description)
        editor.putStringSet("userInterests", user.interest?.toSet())
        editor.putStringSet("userLikes", user.likes?.toSet())
        editor.putStringSet("userDislikes", user.dislikes?.toSet())
        editor.apply()
    }

    private fun clearPreferences() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun loadUserFromPreferences(): User? {
        val userId = sharedPreferences.getString("userId", null) ?: return null
        val userName = sharedPreferences.getString("userName", "") ?: ""
        val userEmail = sharedPreferences.getString("userEmail", "") ?: ""
        val userPassword = sharedPreferences.getString("userPassword", "") ?: ""
        val userConfirmedPassword = sharedPreferences.getString("userConfirmedPassword", "") ?: ""
        val userYearOfBirth = sharedPreferences.getString("userYearOfBirth", "") ?: ""
        val userGender = sharedPreferences.getString("userGender", "") ?: ""
        val userImageUrls =
            sharedPreferences.getStringSet("userImageUrls", emptySet())?.toMutableList()
                ?: mutableListOf()
        val userGenderLookingFor = sharedPreferences.getString("userGenderLookingFor", "") ?: ""
        val userDescription = sharedPreferences.getString("userDescription", "") ?: ""
        val userInterests =
            sharedPreferences.getStringSet("userInterests", emptySet())?.toMutableList()
                ?: mutableListOf()
        val userLikes = sharedPreferences.getStringSet("userLikes", emptySet())?.toMutableList()
            ?: mutableListOf()
        val userDislikes =
            sharedPreferences.getStringSet("userDislikes", emptySet())?.toMutableList()
                ?: mutableListOf()

        return User(
            id = userId,
            name = userName,
            email = userEmail,
            password = userPassword,
            confirmedPassword = userConfirmedPassword,
            yearOfBirth = userYearOfBirth,
            gender = userGender,
            imageUrls = userImageUrls,
            genderLookingFor = userGenderLookingFor,
            description = userDescription,
            interest = userInterests,
            likes = userLikes,
            dislikes = userDislikes
        )
    }

    fun getTestUser(): User {
        return User(
            id = "-O-gTyIykyVTlqOSEkli",
            name = "Usertest",
            email = "user@gmail.com",
            password = "123456789",
            confirmedPassword = "123456789",
            yearOfBirth = "02.06.2024",
            gender = "Male",
            imageUrls = mutableListOf(
"https://firebasestorage.googleapis.com/v0/b/datingapp-9f758.appspot.com/o/UsersImages%2Fimages%2F-O-gTyIykyVTlqOSEkli%2F1718994883704_1000044619?alt=media&token=47b28508-117e-4979-b797-18c9d5500e61"
            ),
            genderLookingFor = "Women",
            description = "usertest",
            interest = mutableListOf("Travel"),
            likes = mutableListOf("-O-gTyIykyVTlqOSEkli"),
            dislikes = mutableListOf()
        )
    }
}
