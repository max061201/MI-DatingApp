package com.MI.DatingApp

import ProfileDetail
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.MI.DatingApp.view.login.Login
import com.MI.DatingApp.view.home.Home
import com.MI.DatingApp.view.likes.Likes
import com.MI.DatingApp.view.matches.Matches
import com.MI.DatingApp.view.profile.ProfileScreen
import com.MI.DatingApp.view.registieren.Registrieren

/**
Navigation throughout the entire APP
 */
@Composable
fun AppNavigation(navController: NavHostController, startDestination: String, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { Login(navController) }
        composable("registrieren") { Registrieren(navController = navController) }
        composable("home") { Home() }
        composable("likes") { Likes() }
        composable("matches") { Matches() }
        composable("profile") { ProfileScreen(navController) }
        composable("screen2") { ProfileDetail(navController) }
    }
}

/**
Navigation Bar throughout the APP
 */
data class NavigationItem(val route: String, val label: String, val iconId: Int)

val bottomNavigationItems = listOf(
    NavigationItem("home", "", R.drawable.home_button),
    NavigationItem("likes", "", R.drawable.herz),
    NavigationItem("matches", "", R.drawable.bote),
    NavigationItem("profile", "", R.drawable.profil)
)
val bottomBarRoutes = setOf("home", "likes","matches","profile")