package com.example.mobilecomputing.ui.edit_reminder

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun EditReminder(
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
            Text("Edit the reminder message ...")
            Spacer(modifier = Modifier.height(70.dp))
            OutlinedTextField(
                value = title.value,
                onValueChange = { title.value = it},
                label = { Text("Message here...") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.editReminder(ReminderViewModel.getSelectedReminder(), title.value)
                        navController.navigate("home")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .size(55.dp),
                enabled = true,
                shape = MaterialTheme.shapes.small,

                ) {
                Text(text = "Update reminder")
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