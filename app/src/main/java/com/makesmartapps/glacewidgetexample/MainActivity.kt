package com.makesmartapps.glacewidgetexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makesmartapps.glacewidgetexample.ui.theme.GlaceWidgetExampleTheme
import com.makesmartapps.glacewidgetexample.ui.theme.RestApiImpl
import com.makesmartapps.glacewidgetexample.ui.theme.RetrofitBuilder
import com.makesmartapps.glacewidgetexample.ui.theme.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlaceWidgetExampleTheme {
                //TaskListScreen()
                exampleFlow()
            }
        }
    }
}

@Composable
fun TaskListScreen() {

    val viewModel: MainActivityViewModel = viewModel(
        factory = MainViewModelFactory(RestApiImpl(RetrofitBuilder.apiService))
    )

    val mainState by viewModel.mainState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.userIntent.send(TaskIntent.FetchTodoTasks)
    }

    when (val state = mainState) {
        is TaskState.Loading -> {
            // Muestra un indicador de carga

        }

        is TaskState.LoadTasks -> {
            // Muestra la lista de tareas
            LazyColumn {
                items(items = state.todoTasks) { task ->
                    TaskItem(task = task)
                }
            }
        }

        is TaskState.Error -> {
            // Muestra un mensaje de error
        }

        else -> {
            // Puedes manejar otros estados aquí
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = task.name,
                color = if (task.state == StateTack.COMPLETED) Color.Gray else Color.Black,
                style = if (task.state == StateTack.COMPLETED) MaterialTheme.typography.body1.copy(
                    textDecoration = TextDecoration.LineThrough
                ) else MaterialTheme.typography.body1
            )
            Text(text = task.date)
            Checkbox(
                checked = task.state == StateTack.COMPLETED,
                onCheckedChange = { isChecked ->
                    // Lógica para cambiar el estado de la tarea (PENDING/DONE)
                    // Dependiendo de cómo esté implementado tu ViewModel
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewGreeting() {
    TaskItem(Task(0, "Hola", "32-12-1991", StateTack.COMPLETED))
}

data class Message(val sender: String, val content: String)

fun exampleFlow() = runBlocking<Unit> {
    // Crear un flujo de mensajes simulados
    val messageFlow: Flow<Message> = flow {
        // Simular mensajes entrantes
        val messages = listOf(
            Message("Usuario 1", "Hola, ¿cómo estás?"),
            Message("Usuario 2", "¡Hola! Estoy bien, ¿y tú?"),
            Message("Usuario 1", "Estoy bien, gracias."),
            Message("Usuario 2", "Eso es genial."),
            Message("Usuario 1", "¿Qué has estado haciendo?"),
            Message("Usuario 2", "Trabajando en un proyecto de programación reactiva."),
            Message("Usuario 1", "¡Qué interesante! Cuéntame más."),
            Message("Usuario 2", "Claro, estoy aprendiendo a usar Flow en Kotlin.")
        )

        // Emitir cada mensaje con un retraso simulado
        messages.forEach { message ->
            emit(message)
            delay(1000) // Simular un retraso de 1 segundo entre mensajes
        }
    }

    // Observar el flujo de mensajes e imprimirlos en la consola
    messageFlow.collect { message ->
        Log.i("jule ","${message.sender}: ${message.content}")
        //println("${message.sender}: ${message.content}")
    }
}


