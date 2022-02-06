package com.example.mobilecomputing.ui.home.reminderManager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobilecomputing.Graph
import com.example.mobilecomputing.data.Reminder
import com.example.mobilecomputing.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ReminderViewState())

    val state: StateFlow<ReminderViewState>
        get() = _state

    suspend fun saveReminder(reminder: Reminder): Long {
        return reminderRepository.addReminder(reminder)
    }

    suspend fun deleteReminder(){
        reminderRepository.deleteReminder()
    }

    init{
        viewModelScope.launch {
            reminderRepository.reminders().collect { list ->
                _state.value = ReminderViewState(
                    reminders = list
                )
            }
        }
    }
}
/*    private val _state = MutableStateFlow(ReminderViewState())
    val state: StateFlow<ReminderViewState>
        get() = _state

    init {
        val list = mutableListOf<Reminder>()
        for (x in 1..20){
            list.add(
                Reminder(
                    id = x.toLong(),
                    title = "$x reminder",
                    date = "Date()"
                )
            )
        }

        viewModelScope.launch {
            _state.value = ReminderViewState(
                reminders = list
            )
        }
    }*/

data class ReminderViewState(
    val reminders: List<Reminder> = emptyList()
)
