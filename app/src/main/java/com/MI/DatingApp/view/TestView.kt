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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.MI.DatingApp.viewModel.MainViewModel
import com.MI.DatingApp.viewModel.registering.RegisteringVM


import coil.compose.rememberImagePainter
import com.MI.DatingApp.model.User

@Composable
fun TestView(navController: NavController) {
    val mainViewModel: MainViewModel = viewModel()
    val registeringVM: RegisteringVM = viewModel()

    val count by mainViewModel.number.observeAsState(0)
    val text by mainViewModel.text.observeAsState("")
    val name by mainViewModel.name.observeAsState("")
    val password by mainViewModel.password.observeAsState("")
    val users by mainViewModel.users.observeAsState(emptyList())
    val currentUser by mainViewModel.currentShownUser.observeAsState()

    val statusMessage by mainViewModel.statusMessage.observeAsState("")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is registrieren", color = Color.Black)

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

        // Button zum Senden der Daten
        Button(onClick = {
            //mainViewModel.saveRegData()
            mainViewModel.getAllUsersData()
        }) {
            Text("get all user")
        }
        Button(onClick = {
            //mainViewModel.saveRegData()
        }) {
            Text("next")
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Text("Anzahl der Klicks: $count", color = Color.Black)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Eingegebener Text: $text", color = Color.Black)

        // Statusnachricht anzeigen
        if (statusMessage.isNotEmpty()) {
            Text(statusMessage, color = Color.Red)
        }
        // Aktuellen Benutzer anzeigen
        currentUser?.let {
            UserRow(user = it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigationsbuttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { mainViewModel.showPreviousUser() }) {
                Text("Previous")
            }
            Button(onClick = { mainViewModel.showNextUser() }) {
                Text("Next")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { mainViewModel.Dislike() }) {
                Text("Dislike")
            }
            Button(onClick = { mainViewModel.Like() }) {
                Text("Like")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("${mainViewModel.momentanerUser2?.name}", color = Color.Black)
        Text(" ${mainViewModel.momentanerUser2?.description}", color = Color.Black)
        Text("  ${mainViewModel.momentanerUser2?.yearOfBirth}", color = Color.Black)

//        ButtonCompose({
//
//
//            //registeringVM.saveUserInFirebaseAuth()
//            navController.navigate("login")
//        }, text = "Create Account reg VM")


    }
}
@Composable
fun UserRow(user: User) {
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