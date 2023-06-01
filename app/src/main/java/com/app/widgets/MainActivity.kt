package com.app.widgets

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.app.widgets.service.WidgetService
import com.app.widgets.ui.theme.WidgetsTheme
import com.app.widgets.ui.widgets.index.WidgetsIndexScreen
import com.converter.ui.compose.SystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var widgetService: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        startService()

        setContent {
            SystemUiController()
            WidgetsTheme { WidgetsIndexScreen() }
        }
    }

    private fun startService() {
        widgetService = Intent(this, WidgetService::class.java)
        startForegroundService(widgetService)
    }
}