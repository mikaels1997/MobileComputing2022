package com.example.mobilecomputing.ui.home.reminderManager

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.util.viewModelProviderFactoryOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Reminders(modifier: Modifier = Modifier, navController: NavController){
    val viewModel: ReminderViewModel = viewModel(
        factory = viewModelProviderFactoryOf { ReminderViewModel()}
    )
    val viewState by viewModel.state.collectAsState()

    Column(modifier = modifier){
        ReminderList(
            list = viewState.reminders,
            viewModel,
            navController
        )
    }
}

@Composable
private fun ReminderList(
    list: List<Reminder>,
    viewModel: ReminderViewModel,
    navController: NavController
) {
    UpdateSeenReminders(viewModel)
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(list){ item ->
            if (item.reminder_seen){
                ReminderListItem(
                    reminder = item,
                    onClick = {},
                    modifier = Modifier.fillParentMaxWidth(),
                    item.creator_id,
                    viewModel,
                    navController
                )
            }
        }
    }
}

@Composable
private fun UpdateSeenReminders(viewModel: ReminderViewModel){
    runBlocking{
        for (reminder in ReminderViewModel.getSeenReminders()){
            viewModel.markAsSeen(reminder.message)
        }
    }
}

@Composable
private fun ReminderListItem(
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    reminder_id: Long = reminder.creator_id,
    viewModel: ReminderViewModel,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    ConstraintLayout(modifier = modifier.clickable { onClick () }) {
        val (divider, reminderTitle, remove, date, edit, category) = createRefs()
        Divider(
            Modifier.constrainAs(divider){
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )
        // Title
        Text(
            text = reminder.message,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderTitle){
                linkTo(
                    start = parent.start,
                    end = remove.start,
                    startMargin = 24.dp,
                    endMargin =  16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // date
        Text(
            text = reminder.creation_time.toDateString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(date){
                linkTo(
                    start = parent.start,
                    end = remove.start,
                    startMargin = 14.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                //centerVerticallyTo(reminderCategory)
                top.linkTo(reminderTitle.bottom, 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
            }
        )

        //Category
        IconButton(
            onClick = {
                coroutineScope.launch {
                    ReminderViewModel.setSelectedReminder(reminder.creator_id)
                    navController.navigate("editcategory")
                }
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(category) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(edit.start, 20.dp)
                }
        ) {
            Icon(
                imageVector = getIconByName(reminder.category),
                contentDescription = "Edit"
            )
        }

        // Edit reminder
        IconButton(
            onClick = {
                coroutineScope.launch {
                    ReminderViewModel.setSelectedReminder(reminder.creator_id)
                    navController.navigate("editreminder")
                }
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(edit) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(remove.start)
                }
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit"
            )
        }

        // Remove reminder
        IconButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.deleteReminderById(reminder_id)
                    }
                },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(remove) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Default.RemoveCircle,
                contentDescription = "Remove"
            )
        }
    }
}

private fun getIconByName(name: String): ImageVector{
    if (name == "money"){
        return Icons.Default.EuroSymbol
    }
    if (name == "work"){
        return Icons.Default.Work
    }
    if (name == "food"){
        return Icons.Default.Fastfood
    }
    if (name == "school"){
        return Icons.Default.School
    }
    if (name == "hobby"){
        return Icons.Default.SportsBasketball
    }
    if (name == "travelling"){
        return Icons.Default.Train
    }
    if (name == "notification"){
        return Icons.Default.Timer
    }
    if (name == "people"){
        return Icons.Default.People
    }
    else
        return Icons.Default.Category
}

private fun Date.formatToString(): String {
    return SimpleDateFormat("MM dd, yyyy", Locale.getDefault()).format(this)
}

private fun Long.toDateString(): String {
    return SimpleDateFormat("MM dd, yyyy", Locale.getDefault()).format(Date(this))
}