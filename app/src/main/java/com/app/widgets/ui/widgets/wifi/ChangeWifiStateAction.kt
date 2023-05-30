package com.app.widgets.ui.widgets.wifi

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver.Companion.ACTION_WIFI_ENABLE_STATUS
import com.app.widgets.ui.widgets.wifi.WifiWidgetReceiver.Companion.ACTION_CHANGE_WIFI_STATE

class ChangeWifiStateAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        val wifiWidgetIntent = Intent(context, WifiWidgetReceiver::class.java).apply {
            this.action = ACTION_CHANGE_WIFI_STATE
        }

        val connectivityWidgetIntent = Intent(context, ConnectivityReceiver::class.java).apply {
            this.action = ACTION_WIFI_ENABLE_STATUS
        }

        context.apply {
            sendBroadcast(wifiWidgetIntent)
            sendBroadcast(connectivityWidgetIntent)
        }
    }

}