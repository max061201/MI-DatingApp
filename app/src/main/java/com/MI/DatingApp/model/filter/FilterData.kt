package com.MI.DatingApp.model.filter

data class FilterData (
    val gender: String = "Male",
    val ageRange: Pair<Int, Int> = 21 to 37
)