package com.MI.DatingApp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.view.MainScreen
import com.MI.DatingApp.ui.theme.ComposeBottomNavigationExampleTheme
import com.MI.DatingApp.viewModel.MainViewModel




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


