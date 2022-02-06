package com.example.mobilecomputing.data.room

import androidx.room.*
import com.example.mobilecomputing.data.Reminder
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class ReminderDao {

    @Query(value = "SELECT * FROM reminders WHERE title = :name")
    abstract suspend fun getReminderWithName(name: String): Reminder?

    @Query("SELECT * FROM reminders WHERE id = :reminderId")
    abstract fun getReminderWithId(reminderId: Long): Reminder?

    @Query("SELECT * FROM reminders LIMIT 15")
    abstract fun reminders(): Flow<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Reminder) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<Reminder>)

    @Update(onConflict =  OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Reminder)

    @Delete
    abstract suspend fun delete(entity: Reminder): Int

    @Query(value = "DELETE FROM reminders")
    abstract suspend fun deleteWithTitle()

}