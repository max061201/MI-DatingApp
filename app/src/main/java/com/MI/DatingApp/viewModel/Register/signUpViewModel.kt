package com.MI.DatingApp.viewModel.Register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.User
import com.MI.DatingApp.model.UserFirebase

class SignUpViewModel : ViewModel() {
    var user by mutableStateOf(User())
        private set

    var predefinedInterests = listOf("Travel", "Shopping", "Netflix", "Sports", "Music")

    fun onNameChange(newValue: String) {
        user = user.copy(name = newValue)
    }

    fun onEmailChange(newValue: String) {
        user = user.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        user = user.copy(password = newValue)
    }

    fun onConfirmPasswordChange(newValue: String) {
        user = user.copy(confirmedPassword = newValue)
    }

    fun onYearOfBirthChange(newValue: String) {
        user = user.copy(yearOfBirth = newValue)
    }

    fun onGenderChange(newValue: String) {
        user = user.copy(gender = newValue)
    }

    fun onLookingForChange(newValue: String) {
        user = user.copy(genderLookingFor = newValue)
    }

    fun onDescribesYouChange(newValue: String) {
        user = user.copy(description = newValue)
    }

    fun onInterestsChange(newValue: String) {
        user = user.copy(interest = newValue.split(",").map { it.trim() }.toMutableList())
    }

    fun addInterest(interest: String) {
        val currentInterests = user.interest.toMutableList()
        if (!currentInterests.contains(interest)) {
            currentInterests.add(interest)
            user = user.copy(interest = currentInterests)
        }
    }

    fun onAddPhoto() {
        // Implement image picker logic
    }
}