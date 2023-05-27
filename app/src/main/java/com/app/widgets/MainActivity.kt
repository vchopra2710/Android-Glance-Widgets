package com.app.widgets

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.app.widgets.receiver.wifiStateReceiver
import com.app.widgets.ui.theme.WidgetsTheme
import com.app.widgets.ui.widgets.index.WidgetsIndexScreen
import com.app.widgets.utils.wifiNetworkCallback
import com.converter.ui.compose.SystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var networkCallback: ConnectivityManager.NetworkCallback? = null

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

        if (networkCallback == null) {
            networkCallback = wifiNetworkCallback(this)

            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val request = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
            networkCallback?.let {
                connectivityManager.registerNetworkCallback(request, it)
            }

        }
    }
}