package com.MI.DatingApp.view

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
import androidx.compose.material.*
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.MI.DatingApp.view.registieren.recyclable.ButtonCompose
import com.MI.DatingApp.viewModel.MainViewModel
import com.MI.DatingApp.viewModel.registering.RegisteringVM

@Composable
fun TestViewModel(navController: NavController) {
    val mainViewModel: MainViewModel = viewModel()
    val registeringVM: RegisteringVM = viewModel()

    val count by mainViewModel.number.observeAsState(0)
    val text by mainViewModel.text.observeAsState("")
    val name by mainViewModel.name.observeAsState("")
    val password by mainViewModel.password.observeAsState("")

    val statusMessage by mainViewModel.statusMessage.observeAsState("")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("This is registrieren", color = Color.Black)

        // Eingabefeld für Name
        OutlinedTextField(
            value = name,
            onValueChange = { mainViewModel.onNameChanged(it) },
            label = { Text("Name", color = Color.Black) },
            textStyle = LocalTextStyle.current.copy(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Eingabefeld für Passwort
        OutlinedTextField(
            value = password,
            onValueChange = { mainViewModel.onPasswordChanged(it) },
            label = { Text("Passwort", color = Color.Black) },
            textStyle = LocalTextStyle.current.copy(color = Color.Black)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button zum Senden der Daten
        Button(onClick = {
            mainViewModel.saveRegData()
        }) {
            Text("Daten senden")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Anzahl der Klicks: $count", color = Color.Black)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Eingegebener Text: $text", color = Color.Black)

        // Statusnachricht anzeigen
        if (statusMessage.isNotEmpty()) {
            Text(statusMessage, color = Color.Red)
        }
        ButtonCompose({


            //registeringVM.saveUserInFirebaseAuth()
            navController.navigate("login")
        }, text = "Create Account reg VM")
    }
}

