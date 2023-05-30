package com.app.widgets.ui.widgets.actions

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver
import com.app.widgets.utils.toggleAirplaneMode

class ChangeAirplaneModeStateAction:ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        context.toggleAirplaneMode()
        val connectivityWidgetIntent = Intent(context, ConnectivityReceiver::class.java).apply {
            this.action = ConnectivityReceiver.ACTION_AIRPLANE_ENABLE_STATUS
        }

        context.sendBroadcast(connectivityWidgetIntent)
    }
}