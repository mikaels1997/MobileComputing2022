package com.example.mobilecomputing.ui.registering

import androidx.lifecycle.ViewModel
import com.example.mobilecomputing.Graph
import com.example.mobilecomputing.data.Account
import com.example.mobilecomputing.data.repository.AccountRepository


class AccountViewModel(
    private val accountRepository: AccountRepository = Graph.accountRepository
) : ViewModel() {

    suspend fun saveAccount(account: Account): Long {
        return accountRepository.addAccount(account)
    }

    suspend fun validateAccount(account: Account): Boolean {
        return accountRepository.validateCredentials(account.name, account.password)
    }

    init{

    }
}