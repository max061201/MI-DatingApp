package com.MI.DatingApp.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
CurrentUser is Used to save the logged in user to get his live data and also save in Preferences
 So the user dont have to login always
 */
object CurrentUser {
    private var user: User? = null
    private const val PREFERENCES_NAME = "MyAppPreferences"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseRealTimeDB: DatabaseReference

    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> get() = _userLiveData

    // ValueEventListener für Benutzeraktualisierungen
    private var userValueEventListener: ValueEventListener? = null
    private var isUserValueListenerActive = false

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        firebaseRealTimeDB = FirebaseDatabase.getInstance().getReference("Users")
        // Lade den Benutzer aus den SharedPreferences, falls vorhanden
        user = loadUserFromPreferences()


    }

    fun initializeUser() {
        user = loadUserFromPreferences()
        // Wenn ein Benutzer geladen wurde (d.h. user ist nicht null), dann:
        user?.let {
            // Starte den ValueEventListener, um Änderungen am Benutzer in der Datenbank zu überwachen
            listenToUserChanges(it.id)
            // Aktualisiere den LiveData-Wert, damit die UI-Komponenten darüber informiert werden
            _userLiveData.postValue(it)

            //saveUserToPreferences(_userLiveData.value!!)

        }

    }

    fun setUser(newUser: User) {
        saveUserToPreferences(newUser)
        _userLiveData.postValue(newUser)
        // save new user to FireDB
        // if (newUser != user){firebaseRealTimeDB.child(newUser.id).setValue(newUser)}
        user = newUser

    }
    

    fun getUser(): User? {
        if (user == null) {
            user = loadUserFromPreferences()
        }
        return user
    }

    fun clearUser() {
        user = null
        clearPreferences()
    }

    fun listenToUserChanges(userId: String) {
        // Entferne den alten Listener, falls vorhanden
        userValueEventListener?.let {
            firebaseRealTimeDB.child(userId).removeEventListener(it)
        }

        // Füge einen neuen ValueEventListener zur Überwachung von Benutzeränderungen hinzu
        userValueEventListener = firebaseRealTimeDB.child(userId).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val updatedUser = snapshot.getValue(User::class.java)
                    updatedUser?.let {
                        if (user == null || user != updatedUser) {
                            // Aktualisiere den LiveData des aktuellen Benutzers
                            _userLiveData.postValue(updatedUser)
                            setUser(updatedUser)

                            Log.d("CurrentUserUpdate", updatedUser.toString())
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Behandlung eines Fehlers beim ValueEventListener
                    Log.e("UserUpdateError", error.message)
                }
            }
        )

        // Markiere den ValueEventListener als aktiv
        isUserValueListenerActive = true
    }



    private fun saveUserToPreferences(user: User) {
        val editor = sharedPreferences.edit()
        editor.putString("userId", user.id)
        editor.putString("userName", user.name)
        editor.putString("userEmail", user.email)
        editor.putString("userPassword", user.password)
        editor.putString("userConfirmedPassword", user.confirmedPassword)
        editor.putString("userYearOfBirth", user.yearOfBirth)
        editor.putString("userGender", user.gender)
        editor.putStringSet("userImageUrls", user.imageUrls?.toSet())
        editor.putString("userGenderLookingFor", user.genderLookingFor)
        editor.putString("userDescription", user.description)
        editor.putStringSet("userInterests", user.interest?.toSet())
        editor.putStringSet("userLikes", user.likes?.toSet())
        editor.putStringSet("userDislikes", user.dislikes?.toSet())
        editor.apply()
    }

    private fun clearPreferences() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun loadUserFromPreferences(): User? {
        val userId = sharedPreferences.getString("userId", null) ?: return null
        val userName = sharedPreferences.getString("userName", "") ?: ""
        val userEmail = sharedPreferences.getString("userEmail", "") ?: ""
        val userPassword = sharedPreferences.getString("userPassword", "") ?: ""
        val userConfirmedPassword = sharedPreferences.getString("userConfirmedPassword", "") ?: ""
        val userYearOfBirth = sharedPreferences.getString("userYearOfBirth", "") ?: ""
        val userGender = sharedPreferences.getString("userGender", "") ?: ""
        val userImageUrls =
            sharedPreferences.getStringSet("userImageUrls", emptySet())?.toMutableList()
                ?: mutableListOf()
        val userGenderLookingFor = sharedPreferences.getString("userGenderLookingFor", "") ?: ""
        val userDescription = sharedPreferences.getString("userDescription", "") ?: ""
        val userInterests =
            sharedPreferences.getStringSet("userInterests", emptySet())?.toMutableList()
                ?: mutableListOf()
        val userLikes = sharedPreferences.getStringSet("userLikes", emptySet())?.toMutableList()
            ?: mutableListOf()
        val userDislikes =
            sharedPreferences.getStringSet("userDislikes", emptySet())?.toMutableList()
                ?: mutableListOf()

        return User(
            id = userId,
            name = userName,
            email = userEmail,
            password = userPassword,
            confirmedPassword = userConfirmedPassword,
            yearOfBirth = userYearOfBirth,
            gender = userGender,
            imageUrls = userImageUrls,
            genderLookingFor = userGenderLookingFor,
            description = userDescription,
            interest = userInterests,
            likes = userLikes,
            dislikes = userDislikes
        )
    }

    fun getTestUser(): User {
        // Nutzt die getUser() Funktion, um den aktuellen Benutzer zu erhalten
        val currentUser = getUser()

        // Wenn kein Benutzer gefunden wurde, wird ein Testbenutzer zurückgegeben
        return currentUser ?: User(
            id = "-O-gTyIykyVTlqOSEkli",
            name = "Usertest",
            email = "user@gmail.com",
            password = "123456789",
            confirmedPassword = "123456789",
            yearOfBirth = "02.06.2024",
            gender = "Male",
            imageUrls = mutableListOf(
"https://firebasestorage.googleapis.com/v0/b/datingapp-9f758.appspot.com/o/UsersImages%2Fimages%2F-O-gTyIykyVTlqOSEkli%2F1718994883704_1000044619?alt=media&token=47b28508-117e-4979-b797-18c9d5500e61"
            ),
            genderLookingFor = "Women",
            description = "usertest",
            interest = mutableListOf("Travel","Sport","Film"),
            likes = mutableListOf("-O-gTyIykyVTlqOSEkli"),
            dislikes = mutableListOf()
        )
    }
}
