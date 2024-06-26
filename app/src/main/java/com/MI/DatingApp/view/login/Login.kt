package com.MI.DatingApp.view.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.MI.DatingApp.R
import com.MI.DatingApp.viewModel.LoginViewModel
import com.MI.DatingApp.viewModel.LoginState

/**
All components that build the Login Page
 */

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
            Spacer(modifier = Modifier.height(25.dp))
            TextFieldInput(email, password, viewModel::onEmailChange, viewModel::onPasswordChange)
            LoginStateHandler(loginState, navController)
            Spacer(modifier = Modifier.height(25.dp))
            LoginButtonAndSignUpText ({ viewModel.login() }, navController)
        }

    }
}

/**
LoginHeader
 */
@Composable
fun LoginHeader() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier
                .size(100.dp)
                .clip(MaterialTheme.shapes.large)
        )

        Text(
            text = stringResource(R.string.app_login_title),
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = stringResource(R.string.app_login_subtitle),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
                .width(250.dp)
        )
    }
}


/**
Login button to call backend for Authentication Login
*/
@Composable
fun LoginButtonAndSignUpText(onClick: () -> Unit, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxHeight()
            .padding( top= 60.dp)
    ) {
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
                fontSize = 20.sp,
                lineHeight = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
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
/**
TextFieldInput
 */
@Composable
fun TextFieldInput(email: String,
                   password: String,
                   onEmailChange: (String) -> Unit,
                   onPasswordChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 50.dp)
    ) {
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
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

        Spacer(modifier = Modifier.height(16.dp))

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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
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

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Forgot password?",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable { /* Handle click */ }
            )
        }
    }
}


/**
Handles the navigation if Login Successful
 */
@Composable
fun LoginStateHandler(loginState: LoginState, navController: NavController) {
    when (loginState) {
        is LoginState.Loading -> CircularProgressIndicator()
        is LoginState.Success -> {navController.navigate("home");}
        is LoginState.Error -> Text(text = loginState.message, color = Color.Red)
        else -> {}
    }
}

