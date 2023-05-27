package com.app.widgets.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import com.app.widgets.model.WifiInfo
import com.app.widgets.ui.widgets.connectivity.WifiWidgetReceiver
import com.app.widgets.ui.widgets.connectivity.WifiWidgetReceiver.Companion.KEY_WIFI_INFO
import kotlinx.serialization.json.Json
fun wifiNetworkCallback(context: Context): ConnectivityManager.NetworkCallback =
    object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            val wifiInfo = context.getWifiDetails()
            context.updateWidget(wifiInfo = wifiInfo)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            context.updateWidget(wifiInfo = null)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            context.updateWidget(wifiInfo = null)
        }
    }

private fun Context.updateWidget(wifiInfo: WifiInfo?) {
    val widgetIntent = Intent(this, WifiWidgetReceiver::class.java).apply {
        this.action = WifiWidgetReceiver.ACTION_WIFI_INFO
    }

    widgetIntent.putExtra(
        KEY_WIFI_INFO,
        Json.encodeToString(WifiInfo.serializer(), wifiInfo ?: WifiInfo())
    )

    sendBroadcast(widgetIntent)
}