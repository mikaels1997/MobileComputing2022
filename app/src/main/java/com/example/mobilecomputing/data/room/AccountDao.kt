package com.example.mobilecomputing.data.room

import androidx.room.*
import com.example.mobilecomputing.data.Account
import com.example.mobilecomputing.data.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AccountDao {

    @Query(value = "SELECT * FROM accounts WHERE username = :name")
    abstract suspend fun getReminderWithName(name: String): Account?

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    abstract fun getAccountWithId(accountId: Long): Account?

    @Query(value = "SELECT *  FROM accounts WHERE username = :name AND password = :password")
    abstract suspend fun validateCredentials(name: String, password: String): Account?

    @Query("SELECT * FROM accounts LIMIT 15")
    abstract fun accounts(): Flow<List<Account>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(entity: Account) : Long

    @Update(onConflict =  OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Account)

    @Delete
    abstract suspend fun delete(entity: Account): Int
}