package com.example.mobilecomputing.data.repository

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.data.room.ReminderDao
import kotlinx.coroutines.flow.Flow

class ReminderRepository(
    private val reminderDao: ReminderDao
) {
    fun reminders(): Flow<List<Reminder>> = reminderDao.reminders()
    fun getReminderWithId(reminderId: Long): Reminder? = reminderDao.getReminderWithId(reminderId)

    suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)

    suspend fun deleteReminder() = reminderDao.deleteWithTitle()

    suspend fun editReminder(id: Long, title: String) = reminderDao.editById(id, title)

    suspend fun deleteById(id: Long) = reminderDao.deleteById(id)

    suspend fun editCategory(id: Long, category: String) = reminderDao.editCategory(id, category)
}