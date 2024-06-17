package com.MI.DatingApp.model

data class User(
    val id: String ="",
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var confirmedPassword: String = "",
    var date: String = "",
    var gander: String = "",
    var imageUrl: String? = null,  // Ändere das hier
    var ganderLookingFor: String = "",
    var describes: String = "",
    var interest: MutableList<String> = mutableListOf()
){
    fun isValid(): Boolean {
        // Beispiel für eine einfache Validierungslogik
        return name.isNotBlank() && email.isNotBlank() && describes.isNotBlank()
    }
}