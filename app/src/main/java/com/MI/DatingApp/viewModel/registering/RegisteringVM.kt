package com.MI.DatingApp.viewModel.registering


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.registieren.FirebaseIm
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisteringVM : ViewModel() {

    private val _user = MutableLiveData<User>().apply { value = User() }
    val user: LiveData<User> = _user

    private val _errorField =
        MutableLiveData<MutableList<Error>>().apply { value = emptyList<Error>().toMutableList() }
    val errorField: LiveData<MutableList<Error>> = _errorField

    var firebaseIm = FirebaseIm()

    private var context: Context? = null

    fun setContext(context: Context) {
        this.context = context
    }

    fun setName(name: String) {
        val updatedUser = _user.value?.copy(name = name)
        _user.value = updatedUser
    }

    fun setEmail(email: String) {
        val updatedUser = _user.value?.copy(email = email)
        _user.value = updatedUser
    }

    fun setPassword(password: String) {
        val updatedUser = _user.value?.copy(password = password)
        _user.value = updatedUser
    }

    fun setGander(gander: String) {
        val updatedUser = _user.value?.copy(gander = gander)
        _user.value = updatedUser
    }

    fun setGanderLookingFor(gander: String) {
        val updatedUser = _user.value?.copy(ganderLookingFor = gander)
        _user.value = updatedUser
    }

    fun describe(describe: String) {
        val updatedUser = _user.value?.copy(describes = describe)
        _user.value = updatedUser
    }

    fun setConfirmedPassword(confirmedPassword: String) {
        val updatedUser = _user.value?.copy(confirmedPassword = confirmedPassword)
        _user.value = updatedUser
    }

    fun setInterestes(interest: String) {
        _user.value?.let { currentUser ->
            val updatedInterests = currentUser.interest.toMutableSet().apply {
                if (this.contains(interest)) {
                    this.remove(interest)
                } else {
                    add(interest)
                }

            }

            // Create a new User object with updated interests
            val updatedUser = currentUser.copy(interest = updatedInterests)

            // Update the state
            _user.value = updatedUser
        }

    }

    fun setError(error: MutableList<Error>) {
        _errorField.value = error
    }

    fun setDate(date: String) {
        val updatedUser = _user.value?.copy(date = date)
        _user.value = updatedUser
    }

    fun checkErrorForFirstPage(): Boolean {

        val check = _user.value!!.emptyFields().isEmpty()
        val mutableListOfError: MutableList<Error> = emptyList<Error>().toMutableList()
        if (!check) {
            _user.value!!.emptyFields().forEach {
                findErrorTextAndRemove(mutableListOfError, it)
                mutableListOfError.add(Error(errorType = it, error = true))
            }
            setError(mutableListOfError)
            return false
        } else {
            if (!_user.value!!.matchPassword()) {
                setError(
                    mutableListOf(
                        Error(
                            errorType = "passowrd not match or less than 6 char",
                            error = true
                        )
                    )
                )
                return false
            } else {
                setError(
                    emptyList<Error>().toMutableList()
                )

            }
            return true
        }


    }


    fun checkErrorForSecondPage(): Boolean {
        val check = _user.value!!.date != "" && _user.value!!.date != ""
        if (!check) {
            setError(mutableListOf(Error(errorType = "check Date or Gander", error = true)))
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkDate(date: String) {
        // TODO check if date in future
        //  return (Date.from(Instant.now()) > DateTimeFormatter.ofPattern())
    }

    fun Uri.uriToBitmap(): Bitmap {
        return BitmapFactory.decodeStream(context!!.contentResolver.openInputStream(this))
    }

    fun saveUserInFirebaseAuth() {
        firebaseIm.saveUserAuth(_user.value!!)
    }


}

private fun findErrorTextAndRemove(mutableList: MutableList<Error>, error: String) {
    mutableList.remove(mutableList.find {
        it.errorType == error
    })
}

data class User(
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var confirmedPassword: String = "",
    var date: String = "",
    var gander: String = "",
    var image: Bitmap? = null,
    var ganderLookingFor: String = "",
    var describes: String = "",
    var interest: MutableSet<String> = mutableSetOf(),
) {
    fun emptyFields(): List<String> {
        val emptyFields = mutableListOf<String>()
        if (name.isEmpty()) {
            emptyFields.add("name")
        }
        if (email.isEmpty()||!checkEmailPattern(email)) {

                emptyFields.add("email")


        }
        if (password.isEmpty()) {
            emptyFields.add("password")
        }
        if (confirmedPassword.isEmpty()) {
            emptyFields.add("confirmedPassword")
        }
        return emptyFields
    }

    fun matchPassword(): Boolean {
        return password == confirmedPassword && password.length >= 6
    }

    private fun checkEmailPattern(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        var s= email.matches(emailPattern)
        return s
    }
}

data class Error(
    var errorType: String = "",
    var error: Boolean
)

fun Date.formatAndToString(): String {

    return SimpleDateFormat("dd.MM.yyy", Locale.getDefault()).format(this)
}

