package com.app.widgets.model

import kotlinx.serialization.Serializable

@Serializable
data class ConnectivityInfo(
    val isWifiEnabled: Boolean = false,
    val isBluetoothEnabled: Boolean = false,
    val isAirplaneEnabled: Boolean = true,
)
