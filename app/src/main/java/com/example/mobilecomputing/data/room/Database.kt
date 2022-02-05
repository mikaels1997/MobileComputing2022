package com.example.mobilecomputing.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilecomputing.data.Account
import com.example.mobilecomputing.data.Reminder

/**
 * The [RoomDatabase] for this app
 */
@Database(
    entities = [Reminder::class],
    version = 1,
    exportSchema = false
)
abstract class ReminderDatabase : RoomDatabase(){
    abstract fun reminderDao(): ReminderDao
}

@Database(
    entities = [Account::class],
    version = 1,
    exportSchema = false
)
abstract class AccountDatabase : RoomDatabase(){
    abstract fun accountDao(): AccountDao
}