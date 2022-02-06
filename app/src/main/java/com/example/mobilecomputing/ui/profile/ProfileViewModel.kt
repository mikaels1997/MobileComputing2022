package com.example.mobilecomputing.ui.profile

import androidx.lifecycle.ViewModel
import com.example.mobilecomputing.Graph
import com.example.mobilecomputing.data.Account
import com.example.mobilecomputing.data.repository.AccountRepository

class ProfileViewModel(
    private val accountRepository: AccountRepository = Graph.accountRepository
) : ViewModel()  {

/*    suspend fun getAccountName(account: Account): Long {
        return accountRepository.getAccountWithId()
    }*/

}