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
            id = "-O-_ISsp8d4zdQvVYkTm",
            name = "max",
            email = "max@gmail.com",
            password = "123456789",
            confirmedPassword = "123456789",
            yearOfBirth = "06.12.2001",
            gender = "Male",
            imageUrl = "https://firebasestorage.googleapis.com/v0/b/datingapp-9f758.appspot.com/o/UsersImages%2Fimages%2Fmax%40gmail.com.jpg?alt=media&token=4b7a3f94-05f1-4ff8-bd63-6407a19eeacf",
            ganderLookingFor = "Women",
            description = "Der Firebase master",
            interest = mutableListOf("Travel", "Sport")
        )
    }
}

