package com.makesmartapps.glacewidgetexample

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.appwidget.*
import androidx.glance.layout.*
import androidx.glance.material3.ColorProviders
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.makesmartapps.glacewidgetexample.ui.theme.DarkColorScheme
import com.makesmartapps.glacewidgetexample.ui.theme.LightColorScheme
import androidx.glance.layout.Column
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items

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
            .background(GlanceTheme.colors.background)
            .padding(8.dp)
            .cornerRadius(20.dp)
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
                text = "Title",
                style = TextStyle(
                    color = GlanceTheme.colors.onSurface,
                    fontSize = 24.sp
                )
            )
        }
        Image(
            ImageProvider(R.drawable.ic_add_foreground),
            contentDescription = null,
            colorFilter = ColorFilter.tint(GlanceTheme.colors.onSurface),
            modifier = GlanceModifier.padding(8.dp)
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
                .background(GlanceTheme.colors.onSurface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.name,
                style = TextStyle(
                    fontSize = 14.sp
                ),
                modifier = GlanceModifier.padding(start = 8.dp).defaultWeight()
            )
            Image(
                ImageProvider(
                    if (item.state == StateTack.COMPLETED) {
                        R.drawable.ic_check_task
                    } else {
                        R.drawable.ic_unchecked_task
                    }
                ),
                contentDescription = null,
                modifier = GlanceModifier.padding(8.dp).size(42.dp)
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

