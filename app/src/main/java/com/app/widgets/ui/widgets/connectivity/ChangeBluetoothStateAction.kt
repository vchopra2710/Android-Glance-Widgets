package com.app.widgets.ui.widgets.connectivity

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.app.widgets.utils.openBluetoothPanel

class ChangeBluetoothStateAction:ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        context.openBluetoothPanel()

        val connectivityWidgetIntent = Intent(context, ConnectivityReceiver::class.java).apply {
            this.action = ConnectivityReceiver.ACTION_BLUETOOTH_ENABLE_STATUS
        }

        context.sendBroadcast(connectivityWidgetIntent)
    }
}