package com.MI.DatingApp.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.MI.DatingApp.R
import com.MI.DatingApp.model.User
import com.MI.DatingApp.ui.theme.ComposeBottomNavigationExampleTheme
import com.MI.DatingApp.view.home.HomeScreen
import com.MI.DatingApp.viewModel.LoginViewModel

//@Composable
//fun Screen1(navController: NavController) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
//    ) {
//        Text("This is Screen 1")
//
//        Button(onClick = { navController.navigate("screen2") }) {
//            Text("Go to Screen 2")
//        }
//    }
//}

@Composable
fun Screen2(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Text("This is Screen 2")

        // Explanation Text
        Text(
            "In this example, when navigating to Screen 3, " + "Screen 2 will be removed from the navigation stack. " + "This is achieved using popUpTo with inclusive = true."
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate("screen3") {
                // This will remove Screen 2 from the stack
                popUpTo("screen2") { inclusive = true }
            }
        }) {
            Text("Go to Screen 3")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen3(navController: NavController) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("Enter something") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate("screen4/$text")
        }) {
            Text("Go to Screen 4 with data")
        }
    }
}

@Composable
fun Screen4(navController: NavController, data: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Text("Received data: $data")
    }
}


@Composable
fun Home(navController: NavController, viewModel: LoginViewModel = viewModel()) {

    // Collect user state from the ViewModel
    val user by viewModel.user.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.AppBackground))
            .padding(20.dp)
    ){
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            HomeScreen()

        }
    }

}

@Composable
fun Likes(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "This is the Likes", style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp)
        )
    }
}
@Composable
fun Chat(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "This is the Chat", style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp)
        )
    }
}
@Composable
fun Profile(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "This is the Likes", style = MaterialTheme.typography.headlineSmall.copy(fontSize = 18.sp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    ComposeBottomNavigationExampleTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Home(navController)
        }
    }
}



