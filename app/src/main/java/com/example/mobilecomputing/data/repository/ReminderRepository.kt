package com.example.mobilecomputing.data.repository

import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.data.room.ReminderDao
import kotlinx.coroutines.flow.Flow

class ReminderRepository(
    private val reminderDao: ReminderDao
) {
    fun reminders(): Flow<List<Reminder>> = reminderDao.reminders()
    fun getReminderWithId(reminderId: Long): Reminder? = reminderDao.getReminderWithId(reminderId)

    suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)
}