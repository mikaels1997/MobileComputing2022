package com.example.mobilecomputing.ui.home.reminderManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.from
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.*
import com.example.mobilecomputing.Graph
import com.example.mobilecomputing.Graph.reminderRepository
import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.data.repository.ReminderRepository
import com.example.mobilecomputing.util.NotificationWorker
import com.example.mobilecomputing.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random.Default.nextInt

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ReminderViewState())

    companion object {
        private var selected: Long = 0
        fun getSelectedReminder():Long { return selected }
        fun setSelectedReminder(id: Long) { selected = id}
        private var seenReminders: MutableList<Reminder> = mutableListOf()
        fun getSeenReminders(): List<Reminder> { return seenReminders}
        fun addSeenReminder(reminder: Reminder) { seenReminders.add(reminder)}
        fun instantNotification(reminder: Reminder) {
            setInstantNotification(reminder)
        }
    }

    val state: StateFlow<ReminderViewState>
        get() = _state

    suspend fun saveReminder(reminder: Reminder): Long {
        if (reminder.reminder_time > reminder.creation_time) {
            setOneTimeNotification(reminder, false)
        }
        if (reminder.first_notification - reminder.reminder_time < "-1".toLong()){
            setOneTimeNotification(reminder, true)
        }
        return reminderRepository.addReminder(reminder)
    }

    suspend fun deleteReminder(){
        reminderRepository.deleteReminder()
    }

    suspend fun deleteReminderById(id: Long){
        reminderRepository.deleteById(id)
    }

    suspend fun editReminder(id: Long, title: String){
        reminderRepository.editReminder(id, title)
    }

    suspend fun editCategory(id: Long, category: String){
        reminderRepository.editCategory(id, category)
    }

    suspend fun markAsSeen(message: String){
        reminderRepository.markAsSeen(message, true)
    }

    init{
        createNotificationChannel(context = Graph.appContext)

        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = ReminderViewState(
                    reminders = list
                )
            }
        }
    }
}

private fun setOneTimeNotification(reminder: Reminder, first: Boolean) {
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    var notificationMessage = "Reminder message: ${reminder.message}"
    var delay = reminder.reminder_time - reminder.creation_time
    var title = "New reminder!"
    if (first) {
        delay = reminder.first_notification - reminder.creation_time
        notificationMessage = "Reminder: <${reminder.message}> due in ${reminder.reminder_time-reminder.first_notification}s"
        title = "Reminder due soon!"
    }
    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(delay, TimeUnit.SECONDS)
        .setConstraints(constraints)
        .build()
    workManager.enqueue(notificationWorker)

    // Monitoring for state of work (maybe not needed)
    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever { workInfo ->
            if(workInfo.state == WorkInfo.State.SUCCEEDED) {
                createReminderNotification(reminder, notificationMessage, title)
            } else {
                //createErrorNotification()
            }
        }
}

private fun setInstantNotification(reminder: Reminder){
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
    var notificationMessage = "Reminder message: ${reminder.message}"
    var title = "New reminder!"

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setConstraints(constraints)
        .build()
    workManager.enqueue(notificationWorker)

    // Monitoring for state of work (maybe not needed)
    if (!reminder.reminder_seen){
        createReminderNotification(reminder, notificationMessage, title)
        runBlocking {
            reminderRepository.markAsSeen(reminder.message, true)
        }
    }
}

private fun createNotificationChannel(context: Context) {
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "NotificationChannelName"
        val descriptionText = "NotificationChannelDescriptionText"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        //register the channel with the system
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

private fun createReminderNotification(reminder: Reminder, title: String, notificationMessage: String) {
    val notificationId = (1000..2000).random()
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle(title)
        .setContentText(notificationMessage)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    with(NotificationManagerCompat.from(Graph.appContext)){
        ReminderViewModel.addSeenReminder(reminder)
        if(reminder.notification_enabled){
            notify(notificationId, builder.build())
        }
    }
}

data class ReminderViewState(
    val reminders: List<Reminder> = emptyList()
)
