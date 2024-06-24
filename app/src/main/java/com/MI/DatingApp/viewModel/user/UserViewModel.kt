package com.MI.DatingApp.viewModel.user

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.model.User
import com.MI.DatingApp.model.registieren.FirebaseIm
import com.MI.DatingApp.viewModel.home.FilterViewModel
import com.google.firebase.database.*
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale

class UserViewModel: ViewModel() {

    private val _usersListLiveData = MutableLiveData<List<User>>()
    val usersListLiveData: LiveData<List<User>> get() = _usersListLiveData

    val currentUserLiveData: LiveData<User?> = CurrentUser.userLiveData

    private var firebaseRefUsers: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    private val filterViewModel = FilterViewModel()

    init {
        CurrentUser.initializeUser()
        getAllUsersData()
    }
    fun setAgeRange() {

    }
    // Beispiel für den Zugriff auf die ageRange
    fun getAgeRange(): Pair<Int, Int> {
        return filterViewModel.filterData.value.ageRange
    }

    private var momentanerUser = CurrentUser.getUser()!!
    private val changes = mutableMapOf<String, Any>()
    private var firebaseIm = FirebaseIm()

    fun like(user: User) {
        Log.d("Righswipe", "like")
       // Log.d("_currentShownUser", user.toString())
       // Log.d("momentanerUser", currentUserLiveData.value.toString())

        user.let { currentShownUser ->
            //hat der user den anderen schonmal geliked/ gedisliked
            if (!momentanerUser.likes.contains(currentShownUser.id)
                && !momentanerUser.dislikes.contains(currentShownUser.id) && !currentShownUser.likes.contains(momentanerUser.id) ) {

                //füge den geliked user in likes von CurrentUser
                momentanerUser.likes.add(currentShownUser.id)
                changes["likes"] = momentanerUser.likes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, momentanerUser.id) // Datenbank aktualisieren
                CurrentUser.setUser(momentanerUser) // Lokal CurrentUser aktualisieren
                changes.clear() // Veränderungen löschen
                Log.d("CurrentUser", CurrentUser.getUser().toString())

                currentShownUser.receivedLikes.add(momentanerUser.id)
                changes["receivedLikes"] = currentShownUser.receivedLikes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, currentShownUser.id) // Datenbank aktualisieren
                changes.clear()
                Log.d("currentShownUser receivedLikes", currentShownUser.receivedLikes.toString())


                // CurrentUser.setUser(momentanerUser) updated current userdata
            }

            // hat einer der user denn anderen schonmal geliked?
            //vlt irgenwie pop up mit Match gefunden oder so?
            else if (currentShownUser.likes.contains(momentanerUser.id)){
                Log.d("MATCH", "es gibt ein mensch zwischen ${momentanerUser.name} und ${currentShownUser.name} ")

                //füge den geliked user in likes von CurrentUser
                momentanerUser.likes.add(currentShownUser.id)
                changes["likes"] = momentanerUser.likes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, momentanerUser.id) // Datenbank aktualisieren
                CurrentUser.setUser(momentanerUser) // Lokal CurrentUser aktualisieren
                changes.clear() // Veränderungen löschen
                Log.d("CurrentUser", CurrentUser.getUser().toString())

                //erstelle ein Match
                firebaseIm.createMatch(momentanerUser.id,currentShownUser.id)
            }
            else{
                Log.d("User", "User wurde schon geliked oder gedisliked $currentShownUser.id")
            }
        }

    }

    fun dislike(user: User) {
        Log.d("leftswipe", "dislike")
        // Prüfen, ob der aktuelle Benutzer angezeigt wird und wenn ja, Änderungen hinzufügen
        user.let { currentShownUser ->
            if (!momentanerUser.likes.contains(currentShownUser.id) && !momentanerUser.dislikes.contains(currentShownUser.id)) {                momentanerUser.dislikes.add(currentShownUser.id)
                changes["dislikes"] = momentanerUser.dislikes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, momentanerUser.id) // Datenbank aktualisieren
                Log.d("changes", changes.toString())
                Log.d("momentanerUser", momentanerUser.toString())
                // CurrentUser.setUser(momentanerUser) updated current userdata
            }else{
                Log.d("User", "User wurde schon geliked oder gedisliked $currentShownUser.id")
            }
        }
    }

    fun getAllUsersData() {
        //schaut ob in der FirebasDB sich was ändert wenn ja aktualisiert er _usersListLiveData
        firebaseRefUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userList = mutableListOf<User>()
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null && shouldShowUser(currentUserLiveData.value!!, user)) {
                        userList.add(user)

                    }
                }
                Log.d("Login", userList.toString())
                _usersListLiveData.postValue(userList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
    // Funktion, um zu überprüfen, ob ein Benutzer angezeigt werden soll
    private fun shouldShowUser(currentUser: User, user: User): Boolean {
        //val ageRange = filterViewModel.getAgeRange()
        //Log.d("ageRange", ageRange.toString())

       // val userAge = calculateAge(user.yearOfBirth)

        return user.id != currentUser.id &&
                !currentUser.likes.contains(user.id) &&
                !currentUser.dislikes.contains(user.id)
                && currentUser.genderLookingFor == user.gender
               // && userAge in ageRange.first..ageRange.second
    }

    // Funktion zur Berechnung des Alters aus dem Geburtsdatum
    private fun calculateAge(yearOfBirth: String): Int {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val dob = sdf.parse(yearOfBirth) ?: return 0 // Falls das Parsing fehlschlägt, 0 zurückgeben

        val today = Calendar.getInstance()

        val dobCalendar = Calendar.getInstance()
        dobCalendar.time = dob

        var age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age
    }
}