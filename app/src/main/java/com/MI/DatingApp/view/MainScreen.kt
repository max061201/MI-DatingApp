package com.MI.DatingApp.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.MI.DatingApp.AppNavigation
import com.MI.DatingApp.bottomBarRoutes
import com.MI.DatingApp.bottomNavigationItems
import com.MI.DatingApp.model.CurrentUser


@Composable
fun MainScreen(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

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
        val startDestination = if (CurrentUser.getUser() != null) "home" else "login"
        AppNavigation(navController = navController, startDestination = startDestination, modifier = Modifier.padding(innerPadding) )
    }
}

data class NavigationItem(val route: String, val label: String, val icon: ImageVector)