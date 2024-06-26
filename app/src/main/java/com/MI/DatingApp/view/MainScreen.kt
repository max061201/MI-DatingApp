package com.MI.DatingApp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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

    // Wenn der Benutzer bereits eingeloggt ist, starte den ValueEventListener
    LaunchedEffect(Unit) {
        if (CurrentUser.getUser() != null) {
            CurrentUser.listenToUserChanges(CurrentUser.getUser()!!.id)
        }
    }


    Scaffold(
        modifier = Modifier.background(Color.Red),
        bottomBar = {
            if (currentRoute in bottomBarRoutes) {
                NavigationBar {
                    bottomNavigationItems.forEach {  item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.iconId),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = if (currentRoute == item.route) Color(0xFF6A00F4) else Color.Black)
                            },

                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                }
                            },

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
