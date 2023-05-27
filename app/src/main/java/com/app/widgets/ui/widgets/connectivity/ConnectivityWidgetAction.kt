package com.app.widgets.ui.widgets.connectivity

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.app.widgets.ui.widgets.connectivity.ConnectivityWidgetReceiver.Companion.ACTION_CHANGE_WIFI_STATE

class ChangeWifiStateAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        val widgetIntent = Intent(context, ConnectivityWidgetReceiver::class.java).apply {
            this.action = ACTION_CHANGE_WIFI_STATE
        }

        context.sendBroadcast(widgetIntent)
    }

}