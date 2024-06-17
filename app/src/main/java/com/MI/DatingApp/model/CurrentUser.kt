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
}
