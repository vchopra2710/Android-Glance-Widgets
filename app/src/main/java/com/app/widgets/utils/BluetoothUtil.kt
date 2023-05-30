package com.app.widgets.utils

import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.core.content.ContextCompat.getSystemService


fun Context.isBluetoothEnabled(): Boolean {
    val bluetoothManager = getSystemService(this, BluetoothManager::class.java)
    val bluetoothAdapter = bluetoothManager?.getAdapter()
    return bluetoothAdapter?.isEnabled ?: false
}

fun Context.openBluetoothPanel() {
    val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)

}