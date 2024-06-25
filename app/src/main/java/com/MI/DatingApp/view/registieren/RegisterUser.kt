package com.MI.DatingApp.view.RegisterUser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.MI.DatingApp.R
import com.MI.DatingApp.view.recyclableGlobal.LoginRegisterHeader
import com.MI.DatingApp.view.registieren.SignUpScreenStep1
import com.MI.DatingApp.view.registieren.SignUpScreenStep2
import com.MI.DatingApp.view.registieren.SignUpScreenStep3
import com.MI.DatingApp.viewModel.registering.RegisteringVM

@Composable
fun RegisterUser(viewModel: RegisteringVM = viewModel()) {
    var step by remember { mutableIntStateOf(1) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1B1B), Color(0xFFAA3FEC))
                )
            )
            .padding(16.dp)
    ) {
        RegisterHeader()
        when (step) {
            1 -> SignUpScreenStep1(onNextClick = { step = 2 })
            2 -> SignUpScreenStep2(onNextClick = { step = 3 }, onBackClick = { step = 1 })
            3 -> SignUpScreenStep3(onCreateAccountClick = { /* Handle account creation */ }, onBackClick = { step = 2 })
        }

    }
}


@Composable
fun RegisterHeader(){
    LoginRegisterHeader(R.string.app_register_title, R.string.app_register_subtitle)
}
