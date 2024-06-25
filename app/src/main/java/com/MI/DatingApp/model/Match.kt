package com.MI.DatingApp.model

data class Match(
    var id: String,
    val userId1: String,
    val userId2: String,
    val timestamp: Long = System.currentTimeMillis()
)


