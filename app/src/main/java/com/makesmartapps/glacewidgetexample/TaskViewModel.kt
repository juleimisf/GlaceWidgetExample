package com.makesmartapps.glacewidgetexample

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
                Log.i("jule","it " + it.toString())
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
            Log.i("jule","fetchTodoTasks")

            _mainState.value = TaskState.Loading

            _mainState.value = try {
                TaskState.LoadTasks(repository.getTodoTasks())

            }catch (e: Exception){
                TaskState.Error(e.message)

            }

          /*  repository.getTodoTasks().collect {
                Log.i("jule","collect")
                _mainState.value = try {
                    Log.i("jule","try")

                    TaskState.LoadTasks(it)
                } catch (e: Exception) {
                    Log.i("jule","Exception")

                    TaskState.Error(e.message)
                }
            }*/

        }
    }
}
