package com.MI.DatingApp.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.MI.DatingApp.AppNavigation
import com.MI.DatingApp.bottomBarRoutes
import com.MI.DatingApp.bottomNavigationItems
import com.MI.DatingApp.model.CurrentUser
import com.MI.DatingApp.viewModel.LoginViewModel


@Composable
fun MainScreen(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Wenn der Benutzer bereits eingeloggt ist, starte den ValueEventListener
    LaunchedEffect(Unit) {
        if (CurrentUser.getUser() != null) {
            CurrentUser.listenToUserChanges(CurrentUser.getUser()!!.id)
        }
    }


    Scaffold(
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                NavigationBar {
                    bottomNavigationItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = null) },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
       // AppNavigation(navController, Modifier.padding(innerPadding), startDestination)
        val startDestination = if (CurrentUser.getUser() != null) "test" else "login"
        AppNavigation(navController = navController, startDestination = startDestination, modifier = Modifier.padding(innerPadding) )
    }
}

data class NavigationItem(val route: String, val label: String, val icon: ImageVector)