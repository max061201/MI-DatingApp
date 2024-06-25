package com.MI.DatingApp.view.registieren

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.MI.DatingApp.view.recyclableGlobal.OutlinedTextFieldGlobal
import com.MI.DatingApp.viewModel.Register.SignUpViewModel
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.MI.DatingApp.viewModel.registering.RegisteringVM
import java.util.*


@Composable
fun SignUpScreenStep1(viewModel: RegisteringVM = viewModel(), onNextClick: () -> Unit){

}

@Composable
fun SignUpScreenStep2(viewModel: RegisteringVM = viewModel(),  onNextClick: () -> Unit, onBackClick: () -> Unit){

}

@Composable
fun SignUpScreenStep3(viewModel: RegisteringVM = viewModel(), onCreateAccountClick: () -> Unit, onBackClick: () -> Unit){

}