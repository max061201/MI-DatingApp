package com.MI.DatingApp.model

data class User(
    val id: String ="",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var confirmedPassword: String = "",
    var yearOfBirth: String = "",
    var gender: String = "",
    var imageUrl: String? = null,  // Ändere das hier
    var ganderLookingFor: String = "",
    var description: String = "",
    var interest: MutableList<String> = mutableListOf()
){
    fun isValid(): Boolean {
        // Beispiel für eine einfache Validierungslogik
        return name.isNotBlank() && email.isNotBlank() && description.isNotBlank()
    }
}