package com.app.widgets.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AirplaneStateReceiver @Inject constructor() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: return
        if (action != Intent.ACTION_AIRPLANE_MODE_CHANGED) return

        val connectivityWidgetIntent = Intent(context, ConnectivityReceiver::class.java).apply {
            this.action = ConnectivityReceiver.ACTION_AIRPLANE_ENABLE_STATUS
        }

        context?.sendBroadcast(connectivityWidgetIntent)
    }
}