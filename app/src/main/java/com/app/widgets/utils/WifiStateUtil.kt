package com.app.widgets.utils

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi


fun Context.changeWifiState(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) openWifiSettingsPanel()
    else changeState()

    return isWifiEnabled()
}

fun Context.isWifiEnabled(): Boolean = wifiManage().isWifiEnabled

@RequiresApi(Build.VERSION_CODES.Q)
private fun Context.openWifiSettingsPanel() {
    val intent = Intent(Settings.Panel.ACTION_WIFI)
    intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

private fun Context.changeState() {
    val wifiManager = wifiManage()
    wifiManager.isWifiEnabled = !isWifiEnabled()
}

private fun Context.wifiManage(): WifiManager {
    return getSystemService(Context.WIFI_SERVICE) as WifiManager
}