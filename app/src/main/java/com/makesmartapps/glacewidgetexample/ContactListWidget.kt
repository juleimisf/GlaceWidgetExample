package com.makesmartapps.glacewidgetexample

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.layout.*
import androidx.glance.material3.ColorProviders
import com.makesmartapps.glacewidgetexample.ui.theme.DarkColorScheme
import com.makesmartapps.glacewidgetexample.ui.theme.LightColorScheme
import androidx.glance.layout.Column
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.text.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent

object ContactListWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme(colors = colorScheme) {
                WidgetListTaskContent()
            }
        }
    }
}

@Composable
fun WidgetListTaskContent() {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .padding(8.dp)
            .cornerRadius(20.dp)
            .background(ImageProvider(R.drawable.bg_background_white))
    ) {
        Column(modifier = GlanceModifier.padding(8.dp).fillMaxSize()) {
            WidgetHeader()
            Spacer(modifier = GlanceModifier.height(12.dp))
            WidgetBody()
        }
    }
}

@Composable
private fun WidgetHeader() {
    Row(
        modifier = GlanceModifier
            .fillMaxWidth()
            .padding(start = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.End
    ) {
        Column(
            modifier = GlanceModifier
                .fillMaxWidth()
                .defaultWeight()

        ) {
            Text(
                text = "TaskTracker",
                style = TextStyle(
                    color = GlanceTheme.colors.onSurface,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Text(text = "Add new task", style = TextStyle(fontSize = 10.sp))
        Image(
            ImageProvider(R.drawable.ic_add_task_foreground),
            contentDescription = null,
            colorFilter = ColorFilter.tint(GlanceTheme.colors.onSurface),
            modifier = GlanceModifier.size(42.dp)
        )
    }
}

@Composable
private fun WidgetBody() {
    val tasks = remember { mutableStateListOf<Task>() }
    tasks.addAll(generateFakeTask())

    LazyColumn {
        items(tasks.size) { index ->
            TaskItem(item = tasks[index], onTaskStateChange = { updatedTask ->
                tasks[index] = updatedTask
            })
        }
    }
}

@Composable
private fun TaskItem(item: Task, onTaskStateChange: (Task) -> Unit) {
    Column {
        Row(
            modifier = GlanceModifier
                .fillMaxWidth()
                .background(ImageProvider(R.drawable.bg_background)),
            verticalAlignment = Alignment.CenterVertically
        ) {

            var updateStyle = remember { mutableStateOf(
                TextStyle(textDecoration = TextDecoration.LineThrough))
            }

            if(item.state == StateTack.COMPLETED){
                updateStyle.value = TextStyle(textDecoration = TextDecoration.LineThrough)
            }
            else{
                updateStyle.value = TextStyle(textDecoration = TextDecoration.None)
            }

            Column(GlanceModifier.padding(8.dp).defaultWeight()) {
                Text(
                    text = item.name,
                    style = updateStyle.value
                )
                Spacer(modifier = GlanceModifier.height(4.dp))
                Text(
                    text = "Deadline: ${item.date}",
                    style = TextStyle(fontSize = 10.sp, fontStyle = FontStyle.Italic)

                )
            }
            val context = LocalContext.current

            var updateImage = remember { mutableStateOf(item.state) }

            Image(
                ImageProvider(getTaskImageResource(updateImage.value)),
                contentDescription = null,
                modifier = GlanceModifier.padding(8.dp).size(42.dp).clickable {

                    val updatedTask = if (item.state == StateTack.PENDING) {
                        item.copy(state = StateTack.COMPLETED)
                    } else {
                        item.copy(state = StateTack.PENDING)
                    }

                    if (item.state == StateTack.PENDING) {
                        updateImage.value = StateTack.COMPLETED
                    } else {
                        updateImage.value = StateTack.PENDING
                    }

                    if(item.state == StateTack.COMPLETED){
                        updateStyle.value = TextStyle(textDecoration = TextDecoration.LineThrough)
                    }
                    else{
                        updateStyle.value = TextStyle(textDecoration = TextDecoration.None)
                    }

                    onTaskStateChange(updatedTask)
                    Toast.makeText(
                        context,
                        if (updatedTask.state == StateTack.COMPLETED) "Excellent! Task accomplished." else "Task reverted to not completed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
        Spacer(modifier = GlanceModifier.height(8.dp))
    }
}

private fun getTaskImageResource(state: StateTack): Int {
    return if (state == StateTack.PENDING) R.drawable.ic_uncheck_task_foreground else R.drawable.ic_check_task_foreground
}

fun generateFakeTask() = listOf(
    Task(
        0,
        "Corregir el diseño de la pantalla de inicio en diferentes dispositivos",
        "12-12-2013",
        StateTack.PENDING
    ),
    Task(
        1,
        "Implementar la funcionalidad de compartir contenido en redes sociales",
        "12-12-2013",
        StateTack.COMPLETED
    ),
    Task(
        3,
        "Actualizar las dependencias y bibliotecas del proyecto",
        "12-12-2013",
        StateTack.PENDING
    ),
    Task(
        4,
        "Colaborar en la revisión de código de otros desarrolladores",
        "12-12-2013",
        StateTack.COMPLETED
    )
)


data class Task(
    val id: Int,
    val name: String,
    val date: String,
    val state: StateTack
)

enum class StateTack {
    PENDING, COMPLETED
}


private val colorScheme = ColorProviders(
    light = LightColorScheme,
    dark = DarkColorScheme
)

class ContactListWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = ContactListWidget
}

