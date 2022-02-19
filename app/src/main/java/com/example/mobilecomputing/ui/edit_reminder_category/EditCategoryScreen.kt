package com.example.mobilecomputing.ui.edit_reminder_category

import android.widget.GridLayout
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobilecomputing.ui.home.reminderManager.ReminderViewModel
import com.example.mobilecomputing.util.viewModelProviderFactoryOf
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel:  ReminderViewModel = viewModel()
){
    val coroutineScope = rememberCoroutineScope()

    Surface(){
        Spacer(modifier = Modifier.height(70.dp))
        Text(text="Select a category for the reminder",
        modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(70.dp))
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ){
            val (divider, school, work, hobby, food, money,
                people, travelling, notification) = createRefs()
            Divider(
                Modifier.constrainAs(divider){
                    top.linkTo(parent.top)
                    centerHorizontallyTo(parent)
                    width = Dimension.fillToConstraints
                }
            )

            //School
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        navController.navigate("Home")
                        viewModel.editCategory(ReminderViewModel.getSelectedReminder(), "school")
                    }
                },
                modifier = Modifier
                    .size(80.dp)
                    .padding(6.dp)
                    .constrainAs(school) {
                        top.linkTo(parent.top, 10.dp)
                        start.linkTo(parent.start)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "School"
                )
            }

            //Work
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        navController.navigate("Home")
                        viewModel.editCategory(ReminderViewModel.getSelectedReminder(), "work")
                    }
                },
                modifier = Modifier
                    .size(80.dp)
                    .padding(6.dp)
                    .constrainAs(work) {
                        top.linkTo(parent.top, 10.dp)
                        start.linkTo(school.end)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Work,
                    contentDescription = "Work"
                )
            }

            //Hobby
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        navController.navigate("Home")
                        viewModel.editCategory(ReminderViewModel.getSelectedReminder(), "hobby")
                    }
                },
                modifier = Modifier
                    .size(80.dp)
                    .padding(6.dp)
                    .constrainAs(hobby) {
                        top.linkTo(parent.top, 10.dp)
                        start.linkTo(work.end)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.SportsBasketball,
                    contentDescription = "Free time"
                )
            }

            //food
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        navController.navigate("Home")
                        viewModel.editCategory(ReminderViewModel.getSelectedReminder(), "food")
                    }
                },
                modifier = Modifier
                    .size(80.dp)
                    .padding(6.dp)
                    .constrainAs(food) {
                        top.linkTo(parent.top, 10.dp)
                        start.linkTo(hobby.end)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = "Food"
                )
            }

            //money
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        navController.navigate("Home")
                        viewModel.editCategory(ReminderViewModel.getSelectedReminder(), "money")
                    }
                },
                modifier = Modifier
                    .size(80.dp)
                    .padding(6.dp)
                    .constrainAs(money) {
                        top.linkTo(school.bottom, 10.dp)
                        start.linkTo(parent.start)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.EuroSymbol,
                    contentDescription = "Money"
                )
            }

            //Social
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        navController.navigate("Home")
                        viewModel.editCategory(ReminderViewModel.getSelectedReminder(), "people")
                    }
                },
                modifier = Modifier
                    .size(80.dp)
                    .padding(6.dp)
                    .constrainAs(people) {
                        top.linkTo(work.bottom, 10.dp)
                        start.linkTo(money.end)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription = "People"
                )
            }

            //Travelling
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        navController.navigate("Home")
                        viewModel.editCategory(ReminderViewModel.getSelectedReminder(), "travelling")
                    }
                },
                modifier = Modifier
                    .size(80.dp)
                    .padding(6.dp)
                    .constrainAs(travelling) {
                        top.linkTo(hobby.bottom, 10.dp)
                        start.linkTo(people.end)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Train,
                    contentDescription = "Travelling"
                )
            }

            //Notification
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        navController.navigate("Home")
                        viewModel.editCategory(ReminderViewModel.getSelectedReminder(), "notification")
                    }
                },
                modifier = Modifier
                    .size(80.dp)
                    .padding(6.dp)
                    .constrainAs(notification) {
                        top.linkTo(food.bottom, 10.dp)
                        start.linkTo(travelling.end)
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = "Notification"
                )
            }
        }
    }
}

/*
@Composable
@Preview
fun Preview(){
    CategoryScreen()
}*/
