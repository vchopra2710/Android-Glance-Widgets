package com.app.widgets

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.app.widgets.receiver.airplaneStateReceiver
import com.app.widgets.receiver.bluetoothStateReceiver
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
        val intentWifi = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiStateReceiver, intentWifi)

        val bluetoothReceiver = bluetoothStateReceiver()
        val intentBluetooth = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(bluetoothReceiver, intentBluetooth)

        val airplaneReceiver = airplaneStateReceiver()
        val intentAirplane = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneReceiver, intentAirplane)

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