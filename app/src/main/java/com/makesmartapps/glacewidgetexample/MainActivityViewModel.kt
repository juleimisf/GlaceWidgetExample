package com.makesmartapps.glacewidgetexample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val repository: MainRepository
) : ViewModel() {

    val userIntent = Channel<TaskIntent>(Channel.UNLIMITED)
    private val _mainState = MutableStateFlow<TaskState>(TaskState.Idle)

    val mainState: StateFlow<TaskState>
        get() = _mainState

    init {
        handleIntent()
    }

    private fun handleIntent(){
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it){
                    is TaskIntent.FetchTodoTasks -> fetchTodoTasks()
                    is TaskIntent.AddTask -> fetchTodoTasks()
                    is TaskIntent.CompleteTask -> fetchTodoTasks()
                    is TaskIntent.DeleteTask -> fetchTodoTasks()
                }
            }
        }
    }

    private suspend fun fetchTodoTasks(){
        viewModelScope.launch {
            _mainState.value = TaskState.Loading

            _mainState.value = try{
                TaskState.LoadTasks(repository.getTodoTasks())
            }catch (e: Exception){
                TaskState.Error(e.message)
            }
        }
    }
}
