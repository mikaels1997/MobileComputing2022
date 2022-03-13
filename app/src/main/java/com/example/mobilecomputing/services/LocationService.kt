package com.example.mobilecomputing.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.mobilecomputing.Graph
import com.example.mobilecomputing.R
import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.data.repository.ReminderRepository
import com.example.mobilecomputing.ui.home.reminderManager.ReminderViewModel
import com.example.mobilecomputing.ui.maps.flattenToList
import com.example.mobilecomputing.util.NotificationWorker
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.TimeUnit

class LocationService :  Service() {

    var isServiceStarted = false
    private val NOTIFICATION_CHANNEL_ID = "my_notification_location"
    var mLocation: Location? = null

    override fun onCreate() {
        super.onCreate()
        isServiceStarted = true
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setOngoing(false)
                .setSmallIcon(R.drawable.ic_launcher_background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_ID, NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.description = NOTIFICATION_CHANNEL_ID
            notificationChannel.setSound(null, null)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(1, builder.build())
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val timer = Timer()
        LocationHelper().startListeningUserLocation(
            this, object : MyLocationListener {
                override fun onLocationChanged(location: Location?) {
                    mLocation = location
                    runBlocking {
                        for (reminder in Graph.reminderRepository.reminders().first()) {
                            notifyIfNear(reminder, mLocation)
                        }
                    }
                }
            })
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

private fun notifyIfNear(reminder: Reminder, location: Location?) {
    val startPoint = Location("locationA")
    if(location != null){
        startPoint.setLatitude(location.latitude)
        startPoint.setLongitude(location.longitude)
        val endPoint = Location("locationA")
        endPoint.setLatitude(reminder.location_y)
        endPoint.setLongitude(reminder.location_x)
        val distance = startPoint.distanceTo(endPoint).toDouble()
        if (distance < 300) {
            ReminderViewModel.instantNotification(reminder)
        }
    }
}