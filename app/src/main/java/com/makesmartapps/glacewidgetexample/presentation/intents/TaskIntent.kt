package com.makesmartapps.glacewidgetexample.presentation.intents

import com.makesmartapps.glacewidgetexample.domain.Task

sealed class TaskIntent {
    object FetchTodoTasks : TaskIntent()
    data class AddTask(val task: Task) : TaskIntent()
    data class CompleteTask(val taskId: String) : TaskIntent()
    data class DeleteTask(val taskId: String) : TaskIntent()
}