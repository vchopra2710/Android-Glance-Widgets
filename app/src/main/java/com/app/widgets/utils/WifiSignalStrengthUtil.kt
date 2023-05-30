package com.app.widgets.utils

import com.app.widgets.R
fun Int.toWifiSignalIcon(): Int = when (this) {
    in -55..0 -> R.drawable.signal_wifi_4_bar
    in -66..-56 -> R.drawable.signal_wifi_3_bar
    in -77..-67 -> R.drawable.signal_wifi_2_bar
    in -88..-78 -> R.drawable.signal_wifi_1_bar
    in -100..-89 -> R.drawable.signal_wifi_0_bar
    else -> R.drawable.signal_wifi_0_bar
}