package com.example.mobilecomputing.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobilecomputing.Graph
import com.example.mobilecomputing.Graph.reminderRepository
import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel(){

    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState>
        get() = _state

    init {
/*        viewModelScope.launch {
            combine(
                reminderRepository.reminders().onEach{ list ->

                }
            ){
                reminders -> HomeViewState(
                    reminders = reminders
                )
            }.collect { _state.value = }
        }*/

        loadReminders()
    }

    private fun loadReminders(){
        val list = mutableListOf<Reminder>()
        viewModelScope.launch {
            list.forEach { reminder -> reminderRepository.addReminder(reminder) }
        }
    }
}

data class HomeViewState(
    val reminders: List<Reminder> = emptyList()
)