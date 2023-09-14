package com.makesmartapps.glacewidgetexample

import com.makesmartapps.glacewidgetexample.ui.theme.Task

sealed class TaskState {
    object Loading: TaskState()
    data class LoadTasks(val todoTasks: List<Task>): TaskState()
    data class Error(val error: String?): TaskState()
}