package com.MI.DatingApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.ui.Modifier

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.MI.DatingApp.ui.theme.ComposeBottomNavigationExampleTheme

import com.MI.DatingApp.view.registieren.Registrieren
import com.MI.DatingApp.viewModel.registering.RegisteringVM

class RegisteringActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModule = ViewModelProvider(this)[RegisteringVM::class.java]

        setContent {
            val navController = rememberNavController()
            ComposeBottomNavigationExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Registrieren(navController,viewModule,this)
                }
            }
        }
    }
}



