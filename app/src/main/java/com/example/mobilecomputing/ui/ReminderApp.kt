package com.example.mobilecomputing.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mobilecomputing.ReminderAppState
import com.example.mobilecomputing.rememberAppState
import com.example.mobilecomputing.ui.addreminder.AddReminder
import com.example.mobilecomputing.ui.home.Home
import com.example.mobilecomputing.ui.login.Login
import com.example.mobilecomputing.ui.profile.ProfileScreen
import com.example.mobilecomputing.ui.registering.SignIn

@Composable
fun ReminderApp(
    appState: ReminderAppState = rememberAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login"){
            Login(navController = appState.navController)
            //Login()
        }
        composable(route = "home"){
            Home(navController = appState.navController)
        }
        composable(route =  "addreminder"){
            AddReminder(navController = appState.navController)
        }
        composable(route = "signin"){
            SignIn(navController = appState.navController)
        }
        composable(route = "profile"){
            ProfileScreen(navController = appState.navController)
        }
    }
}