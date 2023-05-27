package com.app.widgets

import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.app.widgets.receiver.wifiStateReceiver
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

    override fun onStart() {
        super.onStart()
        val wifiStateReceiver = wifiStateReceiver()
        val intent = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intent)
    }
}