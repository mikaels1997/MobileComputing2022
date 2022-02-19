package com.example.mobilecomputing

import android.content.Context
import androidx.room.Room
import com.example.mobilecomputing.data.repository.AccountRepository
import com.example.mobilecomputing.data.repository.ReminderRepository
import com.example.mobilecomputing.data.room.AccountDatabase
import com.example.mobilecomputing.data.room.ReminderDatabase

object Graph {
    lateinit var reminderDatabase: ReminderDatabase
        private set
    lateinit var accountDatabase: AccountDatabase
        private set

    lateinit var appContext: Context

    val reminderRepository by lazy{
        ReminderRepository(
            reminderDao = reminderDatabase.reminderDao()
        )
    }

    val accountRepository by lazy{
        AccountRepository(
            accountDao = accountDatabase.accountDao()
        )
    }

    fun provide(context: Context) {
        appContext = context
        reminderDatabase = Room.databaseBuilder(context, ReminderDatabase::class.java, "data.db")
            .fallbackToDestructiveMigration()
            .build()
        accountDatabase = Room.databaseBuilder(context, AccountDatabase::class.java, "accountData.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}