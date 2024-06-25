package com.MI.DatingApp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.view.MainScreen
import com.MI.DatingApp.ui.theme.ComposeBottomNavigationExampleTheme

//Must haves:
//- Login/ Registrieren, Profil erstellen / Bilder Hochladen (Seiten im PDF 2-6)
//- Homescreen, Liken/ Disliken/ Return (Seiten im PDF 7)
//- Profilansicht der zu liken/dislikenden Person (Seite im PDF 8)
//- Filter Einstellungen (Seite im PDF 9)
//- Profil Bearbeiten, Profil Löschen, Ausloggen (Seite im PDF 14)
//- Anzeige der erhaltenen Likes (Seite im PDF 18)
//Und natürlich die untere Navigation bar

//Nice to Have:
//- Chat (Seite im PDF 15-17)
//- Swipe(Animation) zum Liken/ Disliken (Seite im PDF 10-11)
//- Animation bei Starten der App. (Seite im PDF 1)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CurrentUser.initialize(this)

        setContent {
            val navController = rememberNavController()
            ComposeBottomNavigationExampleTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(navController)
                    //CurrentUser.clearUser()
                    Log.d("CurrentUser", CurrentUser.getUser().toString())
                   // AppContent(navController)
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    ComposeBottomNavigationExampleTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            //MainScreen(navController)
        }
    }
}


