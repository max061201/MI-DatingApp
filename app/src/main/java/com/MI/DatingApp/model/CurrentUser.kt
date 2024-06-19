package com.MI.DatingApp.model

object CurrentUser {
    private var user: User? = null

    fun setUser(newUser: User) {
        user = newUser
    }

    fun getUser(): User? {
        return user
    }

    fun clearUser() {
        user = null
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
                "https://firebasestorage.googleapis.com/v0/b/datingapp-9f758.appspot.com/o/UsersImages%2Fimages%2F-O-gTyIykyVTlqOSEkli%2F1718800867782_msf%3A65?alt=media&token=07b9b71f-0779-4557-8084-f15dd7270efe",
                "https://firebasestorage.googleapis.com/v0/b/datingapp-9f758.appspot.com/o/UsersImages%2Fimages%2F-O-gTyIykyVTlqOSEkli%2Fmsf%3A62?alt=media&token=b171b59b-62f8-496b-94c4-810b7aad7f69"
            ),
            genderLookingFor = "Women",
            description = "usertest",
            interest = mutableListOf("Travel"),
            likes = mutableListOf(),
            dislikes = mutableListOf()
        )
    }
}

