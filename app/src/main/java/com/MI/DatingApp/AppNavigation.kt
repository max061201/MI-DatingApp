package com.MI.DatingApp


import Detail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.MI.DatingApp.login.Login
import com.MI.DatingApp.view.Chat
import com.MI.DatingApp.view.Home
import com.MI.DatingApp.view.Likes
import com.MI.DatingApp.view.NavigationItem
import com.MI.DatingApp.view.Screen3
import com.MI.DatingApp.view.Screen4
import com.MI.DatingApp.view.home.UserDetail
import com.MI.DatingApp.view.profile.ProfileScreen
import com.MI.DatingApp.view.registieren.Registrieren
import com.MI.DatingApp.viewModel.user.UserViewModel


@Composable

fun AppNavigation(navController: NavHostController, startDestination: String,  modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable("login") { Login(navController) }
        composable("registrieren") { Registrieren(navController = navController) }
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

@Composable
fun TestView(navController: NavHostController) {

}

val bottomNavigationItems = listOf(
    NavigationItem("home", "", R.drawable.home_button),
    NavigationItem("likes", "", R.drawable.herz),
    NavigationItem("chat", "", R.drawable.bote),
    NavigationItem("profile", "", R.drawable.profil)
)

val bottomBarRoutes = setOf("home", "likes","chat","profile")