package com.makesmartapps.glacewidgetexample.presentation.ui.activity

import android.os.Bundle
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
import com.makesmartapps.glacewidgetexample.presentation.ui.theme.GlaceWidgetExampleTheme
import com.makesmartapps.glacewidgetexample.data.remote.RestApiImpl
import com.makesmartapps.glacewidgetexample.utils.RetrofitBuilder
import com.makesmartapps.glacewidgetexample.domain.Task
import androidx.lifecycle.viewModelScope
import com.makesmartapps.glacewidgetexample.presentation.viewmodels.MainViewModelFactory
import com.makesmartapps.glacewidgetexample.R
import com.makesmartapps.glacewidgetexample.presentation.intents.TaskIntent
import com.makesmartapps.glacewidgetexample.presentation.states.TaskState
import com.makesmartapps.glacewidgetexample.presentation.ui.widget.StateTack
import com.makesmartapps.glacewidgetexample.presentation.viewmodels.TaskViewModel
import kotlinx.coroutines.launch

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

@Composable
fun ErrorScreen(onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.bg_error),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Disculpa, hubo un error :(  Inténtalo nuevamente.",
            style = TextStyle(fontSize = 18.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Toca aquí para intentarlo nuevamente",
            color = Color.Blue,
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
                    //TODO: Logic for status
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
        CircularProgressIndicator(
            color = Color.Blue,
            modifier = Modifier.size(50.dp)
        )
    }
}

@Preview
@Composable
fun PreviewGreeting() {
    TaskItem(Task(0, "Hola", "32-12-1991", StateTack.COMPLETED))
}



