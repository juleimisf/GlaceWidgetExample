package com.makesmartapps.glacewidgetexample

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.appwidget.*
import androidx.glance.layout.*
import androidx.glance.material3.ColorProviders
import com.makesmartapps.glacewidgetexample.ui.theme.DarkColorScheme
import com.makesmartapps.glacewidgetexample.ui.theme.LightColorScheme
import androidx.glance.layout.Column
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.text.*

object ContactListWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme(colors = colorScheme) {
                WidgetContent()
            }
        }
    }
}

@Composable
fun WidgetContent() {
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
private fun WidgetBody(
) {
    val tasks = generateFakeTask()
    LazyColumn {
        items(tasks) { item ->
            TaskItem(item)
        }
    }
}

@Composable
private fun TaskItem(item: Task) {
    Column {
        Row(
            modifier = GlanceModifier
                .fillMaxWidth()
                .background(ImageProvider(R.drawable.bg_background)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(GlanceModifier.padding(8.dp).defaultWeight()) {
                val textStyle = if (item.state == StateTack.COMPLETED) {
                    TextStyle(
                        color = GlanceTheme.colors.onSurface,
                        textDecoration = TextDecoration.LineThrough
                    )
                } else {
                    TextStyle(color = GlanceTheme.colors.onSurface)
                }
                Text(
                    text = item.name,
                    style = textStyle
                )
                Spacer(modifier = GlanceModifier.height(4.dp))
                Text(
                    text = "Deadline: ${item.date}",
                    style = TextStyle(fontSize = 10.sp, fontStyle = FontStyle.Italic)

                )
            }
            val context = LocalContext.current

            var resource by remember { mutableStateOf(R.drawable.ic_check_task_foreground) }
            if (item.state == StateTack.COMPLETED) {
                resource = R.drawable.ic_check_task_foreground
            } else {
                resource = R.drawable.ic_uncheck_task_foreground
            }
            Image(
                ImageProvider(resource),
                contentDescription = null,
                modifier = GlanceModifier.padding(8.dp).size(42.dp).clickable {
                    if (item.state == StateTack.PENDING) {
                        resource = R.drawable.ic_check_task_foreground
                        Toast.makeText(context, "Excellent! Task accomplished.", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        resource = R.drawable.ic_uncheck_task_foreground
                        Toast.makeText(
                            context,
                            "Task reverted to not completed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }
        Spacer(modifier = GlanceModifier.height(8.dp))
    }
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

