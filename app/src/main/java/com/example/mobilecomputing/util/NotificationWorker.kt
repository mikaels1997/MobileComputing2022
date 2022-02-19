package com.example.mobilecomputing.util

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mobilecomputing.Graph
import com.example.mobilecomputing.R
import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.ui.home.reminderManager.ReminderViewModel
import java.lang.Exception

class NotificationWorker (
    context: Context,
    userParameters: WorkerParameters
) : Worker(context, userParameters){
    override fun doWork(): Result {
        //triggerNotification()
        return try{
            for (i in 0..10){
                Log.i("Notification", "Counted $i")
            }
            Result.success()
        } catch(e: Exception) {
            Result.failure()
        }
    }
}

private fun triggerNotification(){
    val notificationId = (1000..2000).random()
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("New reminder!")
        .setContentText("Reminder message: ")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    with(NotificationManagerCompat.from(Graph.appContext)){
        notify(notificationId, builder.build())
    }
}