package com.MI.DatingApp.model.registieren

import com.MI.DatingApp.model.User


interface RegistierenSaveData {


    fun saveUserAuth(user : User)
    fun saveUserInfo(user :User)
}