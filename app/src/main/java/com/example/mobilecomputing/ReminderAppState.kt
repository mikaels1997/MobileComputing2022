package com.example.mobilecomputing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import  androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mobilecomputing.ui.ReminderApp

class ReminderAppState(
    val navController: NavHostController
) {
    fun NavigateBack(){
        navController.popBackStack()
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController){
    ReminderAppState(navController)
}
