package com.MI.DatingApp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.MI.DatingApp.viewModel.MainViewModel


import coil.compose.rememberImagePainter
import com.MI.DatingApp.model.User

@Composable
fun TestView(navController: NavController, viewModel: MainViewModel = viewModel()) {
   // val mainViewModel: MainViewModel = viewModel()
    //val registeringVM: RegisteringVM = viewModel()

    val count by viewModel.number.observeAsState(0)
    val text by viewModel.text.observeAsState("")
    val name by viewModel.name.observeAsState("")
    val password by viewModel.password.observeAsState("")
    //val users by mainViewModel.users.observeAsState(emptyList())
    // Beobachte die LiveData des aktuellen Benutzers
    val statusMessage by viewModel.statusMessage.observeAsState("")
    val currentUser by viewModel.currentShownUser.observeAsState()


    val currentUserLive by viewModel.currentUserLiveData.observeAsState()

    val users by viewModel.usersListLiveData.observeAsState(emptyList())

    // Wenn die Liste nicht leer ist und currentUser null ist, setzen Sie currentUser auf den ersten Benutzer
    if (users.isNotEmpty() && currentUser == null) {
        viewModel.setCurrentUser(users[0])
    }
    if (users.isEmpty()) {
        viewModel.setCurrentUser(null)
    }

    LaunchedEffect(Unit) {
        viewModel.getAllUsersData()
    }

    LaunchedEffect(users) {
        if (users.isNotEmpty() && currentUser == null) {
            viewModel.showNextUser() // Zeigt den ersten Benutzer an
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is registrieren", color = Color.Black)
        UsersTable(users = users)
//        // Eingabefeld für Name
//        OutlinedTextField(
//            value = name,
//            onValueChange = { mainViewModel.onNameChanged(it) },
//            label = { Text("Name", color = Color.Black) },
//            textStyle = LocalTextStyle.current.copy(color = Color.Black)
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))

//        // Eingabefeld für Passwort
//        OutlinedTextField(
//            value = password,
//            onValueChange = { mainViewModel.onPasswordChanged(it) },
//            label = { Text("Passwort", color = Color.Black) },
//            textStyle = LocalTextStyle.current.copy(color = Color.Black)
//        )


        Spacer(modifier = Modifier.height(16.dp))

//        // Button zum Senden der Daten
//        Button(onClick = {
//            //mainViewModel.saveRegData()
//            viewModel.getAllUsersData()
//        }) {
//            Text("get all user")
//        }
//        Button(onClick = {
//            //mainViewModel.saveRegData()
//        }) {
//            Text("next")
//        }

        Spacer(modifier = Modifier.height(16.dp))

        //Text("Anzahl der Klicks: $count", color = Color.Black)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Swipe example: $text", color = Color.Black)

        // Statusnachricht anzeigen
        if (statusMessage.isNotEmpty()) {
            Text(statusMessage, color = Color.Red)
        }
        // Aktuellen Benutzer anzeigen

        if (currentUser != null) {
            UserRow(user = currentUser!!)
        } else {
            Text("No Users found", color = Color.Black)
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Navigationsbuttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { viewModel.showPreviousUser() }) {
                Text("Previous")
            }
            Button(onClick = { viewModel.showNextUser() }) {
                Text("Next")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { viewModel.Dislike() }) {
                Text("Dislike")
            }
            Button(onClick = { viewModel.Like() }) {
                Text("Like")
            }
        }
        Button(onClick = { viewModel.ChangelookingForGender() }) {
            Text("ChangelookingForGender")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("${currentUserLive?.name}", color = Color.Black)
        Text("${currentUserLive?.description}", color = Color.Black)
        Text("${currentUserLive?.yearOfBirth}", color = Color.Black)
        Text("${currentUserLive?.likes}", color = Color.Black)


//        ButtonCompose({
//
//
//            //registeringVM.saveUserInFirebaseAuth()
//            navController.navigate("login")
//        }, text = "Create Account reg VM")


    }
}
@Composable
fun UsersTable(users: List<User>) {
    LazyColumn {
        items(users.size) { index ->
            UserRow(user = users[index])
        }
    }
}

@Composable
fun UserRow(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Spalte für Name und Email
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = user.name, color = Color.Black)
                Text(text = user.email, color = Color.Black)
            }

            // Spalte für Gender und GenderLookingFor
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Gender: ${user.gender}", color = Color.Black)
                Text(text = "Looking for: ${user.genderLookingFor}", color = Color.Black)
            }

            // Bilder anzeigen, falls vorhanden
            user.imageUrls?.let { imageUrls ->
                Row {
                    imageUrls.forEach { imageUrl ->
                        Image(
                            painter = rememberImagePainter(data = imageUrl),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp) // Größe des Bildes anpassen
                        )
                    }
                }
            }
        }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}



@Composable
fun UserRow2(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        user.imageUrls?.let { imageUrls ->
            Row {
                imageUrls.forEach { imageUrl ->
                    Image(
                        painter = rememberImagePainter(data = imageUrl),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp) // Größe des Bildes anpassen
                    )
                    Spacer(modifier = Modifier.width(4.dp)) // Abstand zwischen Bildern
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = user.name, color = Color.Black)
            Text(text = user.email, color = Color.Black)
        }
    }
}