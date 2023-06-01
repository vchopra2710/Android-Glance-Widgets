package com.app.widgets.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.EXTRA_WIFI_STATE
import android.net.wifi.WifiManager.WIFI_STATE_DISABLED
import android.net.wifi.WifiManager.WIFI_STATE_DISABLING
import android.net.wifi.WifiManager.WIFI_STATE_ENABLED
import android.net.wifi.WifiManager.WIFI_STATE_ENABLING
import android.net.wifi.WifiManager.WIFI_STATE_UNKNOWN
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver.Companion.ACTION_WIFI_ENABLE_STATUS
import com.app.widgets.ui.widgets.wifi.WifiWidgetReceiver
import com.app.widgets.ui.widgets.wifi.WifiWidgetReceiver.Companion.ACTION_UPDATE_WIFI_STATE
import com.app.widgets.utils.wifiNetworkCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WifiStateReceiver @Inject constructor() : BroadcastReceiver() {

    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var connectivityManager: ConnectivityManager? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action != WifiManager.WIFI_STATE_CHANGED_ACTION) return
        val status = when (intent.getIntExtra(EXTRA_WIFI_STATE, WIFI_STATE_UNKNOWN)) {
            WIFI_STATE_ENABLED -> true
            WIFI_STATE_ENABLING,
            WIFI_STATE_DISABLED,
            WIFI_STATE_DISABLING -> false

            else -> null
        } ?: return

        manageWifiCallback(context = context, status = status)

        val wifiWidgetIntent = Intent(context, WifiWidgetReceiver::class.java).apply {
            this.action = ACTION_UPDATE_WIFI_STATE
        }

        val connectivityWidgetIntent = Intent(context, ConnectivityReceiver::class.java).apply {
            this.action = ACTION_WIFI_ENABLE_STATUS
        }

        context?.apply {
            sendBroadcast(wifiWidgetIntent)
            sendBroadcast(connectivityWidgetIntent)
        }
    }

    private fun manageWifiCallback(
        context: Context?,
        status: Boolean
    ) {
        if (status && networkCallback == null && context != null) {

            networkCallback = wifiNetworkCallback(context = context)

            connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val request = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
            networkCallback?.let {
                connectivityManager?.registerNetworkCallback(request, it)
            }
        } else {
            networkCallback?.let { connectivityManager?.unregisterNetworkCallback(it) }
            networkCallback = null
            connectivityManager = null
        }
    }
}