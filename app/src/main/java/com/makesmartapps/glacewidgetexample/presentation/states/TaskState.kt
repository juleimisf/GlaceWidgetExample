package com.makesmartapps.glacewidgetexample.presentation.states

import com.makesmartapps.glacewidgetexample.domain.Task

sealed class TaskState {
    object Loading: TaskState()
    data class LoadTasks(val todoTasks: List<Task>): TaskState()
    data class Error(val error: String?): TaskState()
}