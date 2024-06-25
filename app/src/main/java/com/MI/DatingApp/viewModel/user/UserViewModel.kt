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

    private val changes = mutableMapOf<String, Any>()
    private var firebaseIm = FirebaseIm()

    fun like(user: User) {
        Log.d("Righswipe", "like")
       // Log.d("_currentShownUser", user.toString())
       // Log.d("momentanerUser", currentUserLiveData.value.toString())

        user.let { currentShownUser ->
            var momentanerUser = currentUserLiveData.value!!

            //hat der user den anderen schonmal geliked/ gedisliked
            if (!momentanerUser.likes.contains(currentShownUser.id)
                && !momentanerUser.dislikes.contains(currentShownUser.id) && !momentanerUser.receivedLikes.contains(currentShownUser.id) ) {

                //füge den geliked user in likes von CurrentUser
                momentanerUser.likes.add(currentShownUser.id)

                changes["likes"] = momentanerUser.likes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, momentanerUser.id) // Datenbank aktualisieren
                CurrentUser.setUser(momentanerUser) // Lokal CurrentUser aktualisieren

                changes.clear() // Veränderungen löschen
                Log.d("Righswipe CurrentUser", CurrentUser.getUser().toString())

                currentShownUser.receivedLikes.add(momentanerUser.id)
                changes["receivedLikes"] = currentShownUser.receivedLikes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, currentShownUser.id) // Datenbank aktualisieren
                changes.clear()
                Log.d("Righswipe currentShownUser receivedLikes", currentShownUser.receivedLikes.toString())


                // CurrentUser.setUser(momentanerUser) updated current userdata
            }

            // hat einer der user denn anderen schonmal geliked?
            //vlt irgenwie pop up mit Match gefunden oder so?
            else if (currentShownUser.likes.contains(momentanerUser.id)){
                Log.d("Righswipe MATCH", "es gibt ein mensch zwischen ${momentanerUser.name} und ${currentShownUser.name} ")

                //füge den geliked user in likes von CurrentUser
                momentanerUser.likes.add(currentShownUser.id)
                changes["likes"] = momentanerUser.likes // Änderungen hinzufügen
                firebaseIm.updateUserToDatabase(changes, momentanerUser.id) // Datenbank aktualisieren
                CurrentUser.setUser(momentanerUser) // Lokal CurrentUser aktualisieren
                changes.clear() // Veränderungen löschen
                Log.d("Righswipe CurrentUser", CurrentUser.getUser().toString())

                //erstelle ein Match
                firebaseIm.createMatch(momentanerUser.id,currentShownUser.id)
            }
            else{
                Log.d("Righswipe User", "User wurde schon geliked oder gedisliked $currentShownUser.id")
            }
        }

    }

    fun dislike(user: User) {
        Log.d("leftswipe", "dislike")
        // Prüfen, ob der aktuelle Benutzer angezeigt wird und wenn ja, Änderungen hinzufügen
        user.let { currentShownUser ->
             var momentanerUser = CurrentUser.getUser()!!

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
        lateinit var gender1: String
        var count = 0
        currentUserLiveData.observeForever { currentUser ->
            if (currentUser != null) {
                //val query = firebaseRefUsers.orderByChild("id").startAt(currentUser.id) // das ist für wenn mann nur bestimmte anzahl bzw gholt hat das man bei einer bestimmten id weiter machen kann
                firebaseRefUsers.addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userList = mutableListOf<User>()
                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(User::class.java)
                            val userFemale = userSnapshot.getValue(User::class.java)

                            // Füge Benutzer zur Liste hinzu, wenn sie den Kriterien entsprechen
                            if (user != null && shouldShowUser(currentUser, user)) {
                                userList.add(user)
                            }


                        }
                        // Logge die Liste der Benutzernamen
                        val userNames = userList.map { it.name }
                        Log.d("userList", userNames.joinToString(", "))
                        //_usersListLiveData.value = userList

                        if (count <= 0){
                            // Aktualisiere die LiveData mit der gefilterten Benutzerliste
                            _usersListLiveData.value = userList
                            count++
                            //MALE
                            gender1 = currentUser.genderLookingFor
                            Log.d("userList count 1", userList.toString() + gender1 )

                        }                           //Female      //Female
                        if (gender1.isNotEmpty() && gender1 != currentUser.genderLookingFor){
                            // Aktualisiere die LiveData mit der gefilterten Benutzerliste
                            //Log.d("userList gender", "gender1: $_usersListLiveData.value ")
                            _usersListLiveData.value = userList
                            // Hole die aktuelle Benutzerliste aus LiveData
                            val currentUserList = _usersListLiveData.value ?: emptyList()

                            // Extrahiere die Namen der Benutzer
                            val userNames = currentUserList.map { it.name }

                            // Logge die Namen der Benutzer
                            Log.d("userList gender", "UserList: ${userNames.joinToString(", ")} + Gender: $gender1")


                            //Log.d("userList gender", "_usersListLiveData: ${_usersListLiveData.value} + $gender1")

                            gender1 = currentUser.genderLookingFor
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
            }
        }
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