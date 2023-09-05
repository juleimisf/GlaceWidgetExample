package com.makesmartapps.glacewidgetexample

import com.makesmartapps.glacewidgetexample.ui.theme.Task

sealed class TaskIntent {
    object FetchTodoTasks : TaskIntent()
    data class AddTask(val task: Task) : TaskIntent()
    data class CompleteTask(val taskId: String) : TaskIntent()
    data class DeleteTask(val taskId: String) : TaskIntent()
}