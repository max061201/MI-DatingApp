package com.MI.DatingApp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.MI.DatingApp.R
import com.MI.DatingApp.view.recyclableGlobal.LoginRegisterHeader
import com.MI.DatingApp.view.recyclableGlobal.OutlinedTextFieldGlobal
import com.MI.DatingApp.viewModel.LoginViewModel
import com.MI.DatingApp.viewModel.LoginState

@Composable
fun Login(navController: NavController, viewModel: LoginViewModel = viewModel()) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginState by viewModel.loginState.collectAsState()


    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            navController.navigate("home")
        }
    }

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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            LoginHeader()
            Spacer(modifier = Modifier.height(25.dp))
            TextFieldInput(email, password, viewModel::onEmailChange, viewModel::onPasswordChange)
            LoginStateHandler(loginState, navController)
            Spacer(modifier = Modifier.height(25.dp))
            LoginButtonAndSignUpText ({ viewModel.login() }, navController)
        }

    }
}

@Composable
fun LoginHeader() {
    LoginRegisterHeader(R.string.app_login_title, R.string.app_login_subtitle)
}

@Composable
fun TextFieldInput(email: String,
                   password: String,
                   onEmailChange: (String) -> Unit,
                   onPasswordChange: (String) -> Unit
) {

    val modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)
        .background(Color.Transparent)
        .padding(10.dp)

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 50.dp)
    ) {
        OutlinedTextFieldGlobal(
            textValue = email,
            onValueChanged= onEmailChange,
            label= "email",
            isPassword = false,
            imeAction= ImeAction.Next,
            modifier = modifier
        )


        OutlinedTextFieldGlobal(
            textValue = password,
            onValueChanged= onPasswordChange,
            label= "password",
            isPassword =true,
            imeAction= ImeAction.Done,
            modifier = modifier
        )


        Text(
                text = "Forgot password?",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding( horizontal = 30.dp)
                    .clickable { /* TO DO */ }
        )

    }
}


@Composable
fun LoginButtonAndSignUpText(onClick: () -> Unit, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxHeight()
            .padding( top= 20.dp)

    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "Log In",
                fontSize = 20.sp,
                lineHeight = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }

        ClickableText(
            text = buildAnnotatedString {
                append("Don’t have account? ")
                withStyle(style = SpanStyle(
                    textDecoration = TextDecoration.Underline,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )) {
                    append("Signup")
                }
            },
            style = MaterialTheme.typography.labelSmall,
            onClick = {
                navController.navigate("registrieren")
            }
        )
    }
}

@Composable
fun LoginStateHandler(loginState: LoginState, navController: NavController) {
    when (loginState) {
        is LoginState.Loading -> CircularProgressIndicator()
        is LoginState.Success -> {navController.navigate("home");}
        is LoginState.Error -> Text(text = loginState.message, color = Color.Red)
        else -> {}
    }
}


