package com.makesmartapps.glacewidgetexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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


