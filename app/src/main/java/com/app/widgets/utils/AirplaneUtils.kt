package com.app.widgets.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings


fun Context.isAirplaneEnabled(): Boolean =
    Settings.Global.getInt(
        contentResolver,
        Settings.Global.AIRPLANE_MODE_ON, 0
    ) === 1

/*
* adb shell command to toggle airplane mode
* enable: adb shell settings put global airplane_mode_on 1
* disable: adb shell settings put global airplane_mode_on 0
* */
fun Context.toggleAirplaneMode() {
    val intentAirplaneMode = Intent(Settings.ACTION_WIRELESS_SETTINGS)
    intentAirplaneMode.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intentAirplaneMode)
}