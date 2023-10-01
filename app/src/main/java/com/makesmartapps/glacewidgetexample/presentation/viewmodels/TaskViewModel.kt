package com.makesmartapps.glacewidgetexample.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makesmartapps.glacewidgetexample.presentation.intents.TaskIntent
import com.makesmartapps.glacewidgetexample.presentation.states.TaskState
import com.makesmartapps.glacewidgetexample.domain.TaskRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    val userIntent = Channel<TaskIntent>(Channel.UNLIMITED)
    private val _mainState = MutableStateFlow<TaskState>(TaskState.Loading)

    val mainState: StateFlow<TaskState>
        get() = _mainState

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is TaskIntent.FetchTodoTasks -> fetchTodoTasks()
                    is TaskIntent.AddTask -> fetchTodoTasks()
                    is TaskIntent.CompleteTask -> fetchTodoTasks()
                    is TaskIntent.DeleteTask -> fetchTodoTasks()
                }
            }
        }
    }

    private suspend fun fetchTodoTasks() {
        viewModelScope.launch {
            repository.getTasksFlow()
                .catch { TaskState.Error(it.message) }
                .collect {
                    _mainState.value = TaskState.LoadTasks(it)
                }
        }
    }
}
