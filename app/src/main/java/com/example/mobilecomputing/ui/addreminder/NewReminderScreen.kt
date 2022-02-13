package com.example.mobilecomputing.ui.addreminder

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.ui.home.reminderManager.ReminderViewModel
import com.example.mobilecomputing.ui.home.reminderManager.ReminderViewState
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun AddReminder(
    navController: NavController,
    viewModel:  ReminderViewModel = viewModel()
){
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val title = rememberSaveable { mutableStateOf("") }
    Surface() {

        Column (            modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text("Create a new message for the reminder")
            Spacer(modifier = Modifier.height(70.dp))
            OutlinedTextField(
                value = title.value,
                onValueChange = { title.value = it},
                label = { Text("Title here...") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveReminder(Reminder(
                            message=title.value,
                            date = Date().time,
                            location_x = 0,
                            location_y = 0,
                            reminder_time = 0,
                            creation_time = 0,
                            reminder_seen = false,
                            category = "-"
                            )
                        )

                    }
                    navController.navigate("home")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .size(55.dp),
                enabled = true,
                shape = MaterialTheme.shapes.small,

            ) {
                Text(text = "Save reminder")
            }
        }
    }
}
//joo

@Preview
@Composable
fun Preview(){
    //AddReminder(null)
}