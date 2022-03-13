package com.example.mobilecomputing.data

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "reminders",
    indices = [
        Index("creator_id", unique = true)
    ]
)
data class Reminder (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "creator_id") val creator_id: Long = 0,
    @ColumnInfo(name = "message") val message: String,

    @ColumnInfo(name = "location_x") val location_x: Double,
    @ColumnInfo(name = "location_y") val location_y: Double,
    @ColumnInfo(name = "reminder_time") val reminder_time: Long,
    @ColumnInfo(name = "creation_time") val creation_time: Long,
    @ColumnInfo(name = "reminder_seen") var reminder_seen: Boolean,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "notification_enabled") val notification_enabled: Boolean,
    @ColumnInfo(name = "first_notification") val first_notification: Long = 0,
)