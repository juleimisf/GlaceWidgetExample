package com.makesmartapps.glacewidgetexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makesmartapps.glacewidgetexample.ui.theme.GlaceWidgetExampleTheme
import com.makesmartapps.glacewidgetexample.ui.theme.RestApiImpl
import com.makesmartapps.glacewidgetexample.ui.theme.RetrofitBuilder
import com.makesmartapps.glacewidgetexample.ui.theme.Task
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlaceWidgetExampleTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val viewModel: TaskViewModel = viewModel(
        factory = MainViewModelFactory(RestApiImpl(RetrofitBuilder.apiService))
    )


    TaskListScreen(viewModel)
}

@Composable
fun TaskListScreen(viewModel: TaskViewModel) {
    val mainState by viewModel.mainState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.userIntent.send(TaskIntent.FetchTodoTasks)
    }

    val onRetryClick: () -> Unit = {
        viewModel.viewModelScope.launch {
            viewModel.userIntent.send(TaskIntent.FetchTodoTasks)
        }
    }

    when (mainState) {
        is TaskState.Loading -> {
            LoadingView()
        }

        is TaskState.LoadTasks -> {
            ContentList((mainState as TaskState.LoadTasks).todoTasks)
        }

        is TaskState.Error -> {
            ErrorScreen(onRetryClick = onRetryClick)
        }
    }
}

private fun fetchData() {

}

@Composable
fun ErrorScreen(onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Agregar la imagen SVG
        Image(
            painter = painterResource(id = R.drawable.bg_error), // Reemplaza con el recurso de tu imagen SVG
            contentDescription = null, // Opcional: agregar una descripción accesible
            modifier = Modifier.size(120.dp) // Ajusta el tamaño según tus necesidades
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Agregar el mensaje de error
        Text(
            text = "Disculpa, hubo un error. Inténtalo nuevamente.",
            style = TextStyle(fontSize = 18.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Agregar un botón o un área interactiva para intentar nuevamente
        Text(
            text = "Toca aquí para intentarlo nuevamente",
            color = Color.Blue, // Puedes personalizar el color
            modifier = Modifier.clickable { onRetryClick() }
        )
    }
}


@Composable
fun ContentList(todoTasks: List<Task>) {
    LazyColumn {
        items(items = todoTasks) { task ->
            TaskItem(task = task)
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

@Composable
fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // Fondo blanco
        contentAlignment = Alignment.Center
    ) {
        // Aquí puedes agregar un indicador de carga, como un CircularProgressIndicator
        CircularProgressIndicator(
            color = Color.Blue, // Color del indicador de carga (puedes personalizarlo)
            modifier = Modifier.size(50.dp) // Tamaño del indicador de carga (ajusta según tus necesidades)
        )
    }
}

@Composable
fun ErrorView(error: Throwable) {
    // Aquí puedes mostrar una vista de error y mostrar información sobre el error
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
        Log.i("jule ", "${message.sender}: ${message.content}")
        //println("${message.sender}: ${message.content}")
    }
}


