package com.MI.DatingApp.view.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.MI.DatingApp.viewModel.registering.RegisteringVM

@Composable
fun Register(viewModel: RegisteringVM = viewModel()) {
    var step by remember { mutableStateOf(1) }
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
            when (step) {
                1 -> SignUpScreenStep1(onNextClick = { step = 2 })
                2 -> SignUpScreenStep2(onNextClick = { step = 3 })
                3 -> SignUpScreenStep3(onCreateAccountClick = { /* Handle account creation */ })
            }

    }
}