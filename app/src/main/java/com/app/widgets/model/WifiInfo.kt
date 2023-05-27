package com.app.widgets.model

import kotlinx.serialization.Serializable

@Serializable
data class WifiInfo(
    val isWifiEnabled: Boolean = false,
    val ssid: String = "-",
    val bssid: String = "-",
    val frequency: Int = 0,
    val rssi: Int = 0,
)
