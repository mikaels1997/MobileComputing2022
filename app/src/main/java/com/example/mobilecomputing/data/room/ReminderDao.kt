package com.example.mobilecomputing.data.room

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.*
import com.example.mobilecomputing.data.Reminder
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class ReminderDao {

    @Query(value = "SELECT * FROM reminders WHERE message = :name")
    abstract suspend fun getReminderWithName(name: String): Reminder?

    @Query("SELECT * FROM reminders WHERE creator_id = :reminderId")
    abstract fun getReminderWithId(reminderId: Long): Reminder?

    @Query("SELECT * FROM reminders")
    abstract fun reminders(): Flow<List<Reminder>>

    @Query("SELECT * FROM reminders")
    abstract fun reminderList(): List<Reminder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Reminder) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<Reminder>)

    @Update(onConflict =  OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Reminder)

    @Query(value = "UPDATE reminders SET category = :category WHERE creator_id = :id")
    abstract suspend fun editCategory(id: Long, category: String)

    @Delete
    abstract suspend fun delete(entity: Reminder): Int

    @Query(value = "DELETE FROM reminders WHERE creator_id = :id")
    abstract suspend fun deleteById(id: Long)

    @Query(value = "DELETE FROM reminders")
    abstract suspend fun deleteWithTitle()

    @Query(value = "UPDATE reminders SET message = :title WHERE creator_id = :id")
    abstract suspend fun editById(id: Long, title: String)

    @Query(value = "UPDATE reminders SET reminder_seen = :seen WHERE message = :message ")
    abstract suspend fun markAsSeen(message: String, seen: Boolean)

}