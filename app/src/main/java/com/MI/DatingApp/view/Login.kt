package com.MI.DatingApp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.MI.DatingApp.R
import com.MI.DatingApp.viewModel.LoginViewModel
import com.MI.DatingApp.viewModel.LoginState

@Composable
fun Login(navController: NavController, viewModel: LoginViewModel = viewModel()) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

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
            Spacer(modifier = Modifier.height(16.dp))
            EmailInput(email, viewModel::onEmailChange)
            Spacer(modifier = Modifier.height(16.dp))
            PasswordInput(password, viewModel::onPasswordChange)
            Spacer(modifier = Modifier.height(16.dp))
            LoginButton { viewModel.login() }
            Spacer(modifier = Modifier.height(16.dp))
            SignUpText(navController)
            Spacer(modifier = Modifier.height(16.dp))
            LoginStateHandler(loginState)
        }
    }
}

@Composable
fun LoginHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 150.dp)
    ) {
        val painter = rememberImagePainter("https://www.figma.com/design/lYUFkzubr6iXc0oOu9HMn7/Untitled?node-id=51-45&t=ZLOQrGiVup9wiAam-4")

        Icon(
            painter = painter,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier
                .size(100.dp)
                .clip(MaterialTheme.shapes.large)
        )
        Text(
            text = "Log in to Chat&Meet",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Welcome back! Sign in using your social account or email to continue us",
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .width(250.dp)
        )
    }
}

@Composable
fun EmailInput(email: String, onEmailChange: (String) -> Unit) {
    BasicTextField(
        value = email,
        onValueChange = onEmailChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Transparent)
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp),
        textStyle = TextStyle(color = Color.White),
        decorationBox = { innerTextField ->
            if (email.isEmpty()) {
                Text(
                    text = "Your email",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            innerTextField()
        }
    )
}

@Composable
fun PasswordInput(password: String, onPasswordChange: (String) -> Unit) {
    BasicTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Transparent)
            .border(1.dp, Color.White, RoundedCornerShape(8.dp))
            .padding(16.dp),
        textStyle = TextStyle(color = Color.White),
        visualTransformation = PasswordVisualTransformation(),
        decorationBox = { innerTextField ->
            if (password.isEmpty()) {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            innerTextField()
        }
    )
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
    ) {
        Text(
            text = "Log In",
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black
        )
    }
}

@Composable
fun SignUpText(navController: NavController) {
    ClickableText(
        text = buildAnnotatedString {
            append("Don’t have account? ")
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, color = Color.White)) {
                append("Signup")
            }
        },
        style = MaterialTheme.typography.labelSmall,
        onClick = {
            navController.navigate("registrieren")
        }
    )
}

@Composable
fun LoginStateHandler(loginState: LoginState) {
    when (loginState) {
        is LoginState.Loading -> CircularProgressIndicator()
        is LoginState.Success -> Text(text = "Login Successful", color = Color.White)
        is LoginState.Error -> Text(text = (loginState as LoginState.Error).message, color = Color.Red)
        else -> {}
    }
}

