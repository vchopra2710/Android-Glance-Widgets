package com.app.widgets.receiver

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver.Companion.ACTION_BLUETOOTH_ENABLE_STATUS
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BluetoothStateReceiver @Inject constructor() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action ?: return
        if (action != BluetoothAdapter.ACTION_STATE_CHANGED) return

        when (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)) {
            BluetoothAdapter.STATE_ON,
            BluetoothAdapter.STATE_TURNING_ON -> true

            BluetoothAdapter.STATE_OFF,
            BluetoothAdapter.STATE_TURNING_OFF -> false

            else -> null
        } ?: return

        val connectivityWidgetIntent = Intent(context, ConnectivityReceiver::class.java).apply {
            this.action = ACTION_BLUETOOTH_ENABLE_STATUS
        }

        context?.sendBroadcast(connectivityWidgetIntent)
    }
}