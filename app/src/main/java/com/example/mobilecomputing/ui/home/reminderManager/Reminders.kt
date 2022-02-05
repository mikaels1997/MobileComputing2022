package com.example.mobilecomputing.ui.home.reminderManager

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.util.viewModelProviderFactoryOf
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Reminders(modifier: Modifier = Modifier){
    val viewModel: ReminderViewModel = viewModel(
        factory = viewModelProviderFactoryOf { ReminderViewModel()}
    )
    val viewState by viewModel.state.collectAsState()

    Column(modifier = modifier){
        ReminderList(
            list = viewState.reminders
        )
    }
}

@Composable
private fun ReminderList(
    list: List<Reminder>
) {
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(list){ item ->
            ReminderListItem(
                reminder = item,
                onClick = {},
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}

@Composable
private fun ReminderListItem(
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, reminderTitle, icon, date) = createRefs()
        Divider(
            Modifier.constrainAs(divider){
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // Title
        Text(
            text = reminder.title,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderTitle){
                linkTo(
                    start = parent.start,
                    end = icon.start,
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
            text = reminder.date.toDateString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(date){
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 14.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                //centerVerticallyTo(reminderCategory)
                top.linkTo(reminderTitle.bottom, 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
            }
        )

/*        // Icon
        IconButton(
            onClick = { *//*TODO*//*},
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Check"
            )
        }*/
    }
}


private fun Date.formatToString(): String {
    return SimpleDateFormat("MM dd, yyyy", Locale.getDefault()).format(this)
}

private fun Long.toDateString(): String {
    return SimpleDateFormat("MM dd, yyyy", Locale.getDefault()).format(Date(this))
}