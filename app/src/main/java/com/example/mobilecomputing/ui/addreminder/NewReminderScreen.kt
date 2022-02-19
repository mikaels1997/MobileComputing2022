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
import kotlinx.coroutines.NonDisposableHandle.parent
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
    val notificationDelay = rememberSaveable { mutableStateOf("") }
    val firstNotificationBefore = rememberSaveable { mutableStateOf("0")}
    val notificationEnabled = remember{ mutableStateOf(false)}
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
            OutlinedTextField(
                value = notificationDelay.value,
                onValueChange = { notificationDelay.value = it},
                label = { Text("Notification delay here (seconds)...") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row(modifier = Modifier.padding(8.dp)) {
                Checkbox(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    checked = notificationEnabled.value,
                    onCheckedChange = { notificationEnabled.value = it },
                    enabled = true,
                )
                Text(text = "Notify?", modifier = Modifier.align(Alignment.CenterVertically))
            }
            Row(modifier = Modifier.padding(8.dp)){
                Text(text = "First notification before:",
                    modifier = Modifier.align(Alignment.CenterVertically))
                Spacer(modifier = Modifier.width(20.dp))
                OutlinedTextField(
                    modifier = Modifier.height(70.dp).width(90.dp).align(Alignment.CenterVertically),
                    value = firstNotificationBefore.value,
                    onValueChange = { firstNotificationBefore.value = it},
                    label = { Text("Seconds", modifier = Modifier.align(Alignment.CenterVertically)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveReminder(Reminder(
                            message=title.value,
                            location_x = 0,
                            location_y = 0,
                            reminder_time = Date().time + notificationDelay.value.toLong(),
                            creation_time = Date().time,
                            reminder_seen = false,
                            category = "-",
                            notification_enabled = notificationEnabled.value,
                            first_notification = Date().time +
                                    notificationDelay.value.toLong() -
                                    firstNotificationBefore.value.toLong()
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

@Preview
@Composable
fun Preview(){
    //AddReminder(null)
}