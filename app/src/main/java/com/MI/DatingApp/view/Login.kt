package com.MI.DatingApp.view

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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.MI.DatingApp.R
import com.MI.DatingApp.ui.theme.ComposeBottomNavigationExampleTheme
import com.MI.DatingApp.viewModel.Login2ViewModel
import com.MI.DatingApp.viewModel.LoginViewModel
import com.MI.DatingApp.viewModel.LoginState

@Composable
fun Login(navController: NavController, viewModel: Login2ViewModel = viewModel()) {
    val email by viewModel.email.collectAsState()
    val name by viewModel.name.collectAsState()

    val password by viewModel.password.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            navController.navigate("registrieren")
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
            Spacer(modifier = Modifier.height(32.dp))

            TextFieldInput(name,password, viewModel::onNameChanged, viewModel::onPasswordChanged)
            Spacer(modifier = Modifier.height(32.dp))
            LoginButtonAndSignUpText ({ viewModel.verifyData() }, navController)
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

@Composable
fun TextFieldInput(email: String,
                   password: String,
                   onEmailChange: (String) -> Unit,
                   onPasswordChange: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
            .padding(top = 60.dp)
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


@Composable
fun LoginButtonAndSignUpText(onClick: () -> Unit, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxHeight()
            .padding( top= 40.dp)
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

@Composable
fun LoginStateHandler(loginState: LoginState) {
    when (loginState) {
        is LoginState.Loading -> CircularProgressIndicator()
        is LoginState.Success -> Text(text = "Login Successful", color = Color.White)
        is LoginState.Error -> Text(text = (loginState as LoginState.Error).message, color = Color.Red)
        else -> {}
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    ComposeBottomNavigationExampleTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            MainScreen(navController)
        }
    }
}

