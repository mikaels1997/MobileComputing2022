package com.example.mobilecomputing.data.repository

import com.example.mobilecomputing.data.Account
import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.data.room.AccountDao
import com.example.mobilecomputing.data.room.ReminderDao
import kotlinx.coroutines.flow.Flow

class AccountRepository (
    private val accountDao: AccountDao
        ){
    fun accounts(): Flow<List<Account>> = accountDao.accounts()
    fun getAccountWithId(accountId: Long): Account? = accountDao.getAccountWithId(accountId)

    suspend fun validateCredentials(name: String, password: String): Boolean{
        return accountDao.validateCredentials(name, password) != null
    }

    suspend fun addAccount(account: Account) = accountDao.insert(account)
}