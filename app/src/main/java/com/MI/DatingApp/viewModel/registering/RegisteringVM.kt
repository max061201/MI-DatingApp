package com.MI.DatingApp.viewModel.registering

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.registieren.FirebaseIm
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import android.Manifest
import android.content.pm.PackageManager
import java.util.*

class RegisteringVM : ViewModel() {

    private val _user = MutableLiveData<User>().apply { value = User() }
    val user: LiveData<User> = _user

    private val _errorField = MutableLiveData<MutableList<Error>>().apply { value = emptyList<Error>().toMutableList() }
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

    fun setImagePath(imagePath: String) {
        val updatedUser = _user.value?.copy(imageUrl = imagePath)
        _user.value = updatedUser
    }

    fun setInterestes(interest: String) {
        _user.value?.let { currentUser ->
            val updatedInterests = currentUser.interest.toMutableList().apply {
                if (this.contains(interest)) {
                    this.remove(interest)
                } else {
                    add(interest)
                }
            }
            val updatedUser = currentUser.copy(interest = updatedInterests)
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
                            errorType = "password not match or less than 6 char",
                            error = true
                        )
                    )
                )
                return false
            } else {
                setError(emptyList<Error>().toMutableList())
            }
            return true
        }
    }


    fun checkErrorForSecondPage(): Boolean {
        val check = _user.value!!.date.isNotEmpty() && _user.value!!.gander.isNotEmpty()
        if (!check) {
            setError(mutableListOf(Error(errorType = "check Date or Gender", error = true)))
            return false
        }
        return true
    }

    private var firebaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private var storageRef: StorageReference = FirebaseStorage.getInstance().getReference("UsersImages")

    fun saveUserInFirebaseAuth() {
        val user = _user.value!!
        val imageUri = user.imageUrl

        if (imageUri != null) {
            val uri = Uri.parse(imageUri)
            val imageRef = storageRef.child("images/${user.email}.jpg")
            val uploadTask = imageRef.putFile(uri)
            uploadTask.addOnFailureListener {
                Log.d("Firebase", "Image upload failed: ${it.message}")
            }.addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    saveUserToDatabase(user, imageUrl)
                }
            }
        } else {
            saveUserToDatabase(user, null)
        }
        firebaseIm.saveUserAuth(user)
    }

    private fun saveUserToDatabase(user: User, imageUrl: String?) {
        val userWithImage = user.copy(imageUrl = imageUrl)
        val contactId = firebaseRef.push().key ?: ""
        firebaseRef.child(contactId).setValue(userWithImage)
            .addOnCompleteListener {
                Log.d("Firebase", "User saved successfully")
                // Update user location after saving user data
                updateUserLocation(contactId)
            }
            .addOnFailureListener {
                Log.d("Firebase", "Error saving user: ${it.message}")
            }
    }

    fun updateUserLocation(userId: String) {
        if (context != null) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val userUpdates = mapOf(
                            "lat" to location.latitude,
                            "lng" to location.longitude
                        )
                        firebaseRef.child(userId).updateChildren(userUpdates)
                    }
                }
            } else {
                // Handle permission not granted
            }
        }
    }
}
data class User(
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var confirmedPassword: String = "",
    var date: String = "",
    var gander: String = "",
    var imageUrl: String? = null,
    var ganderLookingFor: String = "",
    var describes: String = "",
    var interest: MutableList<String> = mutableListOf(),
    var lat: Double = 0.0,
    var lng: Double = 0.0
) {
    fun emptyFields(): List<String> {
        val emptyFields = mutableListOf<String>()
        if (name.isEmpty()) emptyFields.add("name")
        if (email.isEmpty() || !checkEmailPattern(email)) emptyFields.add("email")
        if (password.isEmpty()) emptyFields.add("password")
        if (confirmedPassword.isEmpty()) emptyFields.add("confirmedPassword")
        return emptyFields
    }

    fun matchPassword(): Boolean {
        return password == confirmedPassword && password.length >= 6
    }

    private fun checkEmailPattern(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailPattern)
    }
}

data class Error(
    var errorType: String = "",
    var error: Boolean
)

fun Date.formatAndToString(): String {
    return SimpleDateFormat("dd.MM.yyy", Locale.getDefault()).format(this)
}

private fun findErrorTextAndRemove(mutableList: MutableList<Error>, error: String) {
    mutableList.remove(mutableList.find { it.errorType == error })
}

