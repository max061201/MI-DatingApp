package com.MI.DatingApp


import Detail
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.MI.DatingApp.view.Chat
import com.MI.DatingApp.view.Home
import com.MI.DatingApp.view.Likes
import com.MI.DatingApp.view.NavigationItem
import com.MI.DatingApp.view.Screen3
import com.MI.DatingApp.view.Screen4
import com.MI.DatingApp.view.Login
import com.MI.DatingApp.view.RegisterUser.RegisterUser
import com.MI.DatingApp.view.TestView
import com.MI.DatingApp.view.profile.ProfileScreen





@Composable
fun AppNavigation(navController: NavHostController, startDestination: String, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable("login") { Login(navController) }
       // composable("registrieren") { Registrieren(navController = navController) }
        composable("registrieren"){ RegisterUser() }
        composable("test") { TestView(navController) }
        composable("home") { Home(navController) }
        composable("likes") { Likes(navController) }
        composable("chat") { Chat(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("screen2") { Detail(navController) }
        composable("screen3") { Screen3(navController) }
        composable("screen4/{data}", arguments = listOf(navArgument("data") { type = NavType.StringType })) { backStackEntry ->
            Screen4(navController, backStackEntry.arguments?.getString("data") ?: "")
        }
    }
}

val bottomNavigationItems = listOf(
    NavigationItem("home", "Home", Icons.Filled.Home),
    NavigationItem("likes", "Screen 2", Icons.Filled.Favorite),
    NavigationItem("chat", "Screen 3", Icons.Filled.MailOutline),
    NavigationItem("profile", "Screen 4", Icons.Filled.AccountCircle)
)

val bottomBarRoutes = setOf("home", "likes","chat","profile")