package com.example.mobilecomputing.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobilecomputing.R
import com.example.mobilecomputing.ui.home.reminderManager.Reminders
import com.google.accompanist.insets.systemBarsPadding


@Composable
fun Home (
    navController: NavController
){
    Surface(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            navController = navController
        )
    }
}

@Composable
private fun HomeAppBar(
    backgroundColor: Color,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        backgroundColor = backgroundColor,
        actions = {
            IconButton( onClick = {}){
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
            IconButton( onClick = { navController.navigate("profile")}){
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Account")
            }
        }
    )
}

@Composable
fun HomeContent(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "addreminder") },
                contentColor = Color.Black,
                modifier = Modifier.padding(all = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            val appBarColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f)

            HomeAppBar(
                backgroundColor = appBarColor,
                navController = navController
            )

            Reminders(
                modifier = Modifier.fillMaxSize(),
                navController
            )
        }
    }
}

@Preview
@Composable
fun Preview(){
    //Home()
}
