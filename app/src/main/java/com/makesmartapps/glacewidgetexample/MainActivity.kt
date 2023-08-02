package com.makesmartapps.glacewidgetexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.preferencesOf
import androidx.glance.appwidget.ExperimentalGlanceRemoteViewsApi
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.itemsIndexed
import androidx.glance.layout.Box
import com.makesmartapps.glacewidgetexample.ui.theme.GlaceWidgetExampleTheme

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GlaceWidgetExampleTheme {

            }
        }
    }
}

@Composable
fun MyScreen() {
    val nameList = listOf(
        "Daniel Atitienei",
        "John Doe"
    )
    LazyColumn {
        itemsIndexed(nameList) { index, item ->
            Text(text = item)
        }
    }
}


