package com.app.widgets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.app.widgets.ui.theme.WidgetsTheme
import com.app.widgets.ui.widgets.index.WidgetsIndexScreen
import com.converter.ui.compose.SystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            SystemUiController()
            WidgetsTheme { WidgetsIndexScreen() }
        }
    }
}