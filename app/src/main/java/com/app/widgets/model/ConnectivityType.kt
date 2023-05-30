package com.app.widgets.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.glance.action.actionParametersOf
import com.app.widgets.R
import com.app.widgets.ui.widgets.actions.ChangeAirplaneModeStateAction
import com.app.widgets.ui.widgets.actions.ChangeBluetoothStateAction
import com.app.widgets.ui.widgets.connectivity.getClickTypeActionParameterKey
import com.app.widgets.ui.widgets.actions.FlashlightAction
import com.app.widgets.ui.widgets.actions.ChangeWifiStateAction

enum class ConnectivityType(
    @DrawableRes val enabledIcon: Int,
    val enabledBgColor: Color = Color.Blue,
    val enabledIconTint: Color = Color.White,

    @DrawableRes val disabledIcon: Int,
    val disabledBgColor: Color = Color.Black.copy(alpha = 0.5f),
    val disabledIconTint: Color = Color.Gray,
) {
    WIFI(
        enabledIcon = R.drawable.signal_wifi_4_bar,
        disabledIcon = R.drawable.wifi_off,
    ),
    BLUETOOTH(
        enabledIcon = R.drawable.bluetooth_on,
        disabledIcon = R.drawable.bluetooth_off,
    ),
    AIRPLANE(
        enabledIcon = R.drawable.airplanemode_inactive,
        disabledIcon = R.drawable.airplanemode_active,
    ),
    FLASH_LIGHT(
        enabledIcon = R.drawable.flashlight_on,
        disabledIcon = R.drawable.flashlight_off,
    );

    companion object {
        fun ConnectivityType.callbackClass() = when (this) {
            WIFI -> ChangeWifiStateAction::class.java
            BLUETOOTH -> ChangeBluetoothStateAction::class.java
            AIRPLANE -> ChangeAirplaneModeStateAction::class.java
            FLASH_LIGHT -> FlashlightAction::class.java
        }

        fun ConnectivityType.actionParameters(boolean: Boolean) = when (this) {
            FLASH_LIGHT -> actionParametersOf(getClickTypeActionParameterKey() to if (boolean) 0 else 1)
            else -> actionParametersOf()
        }
    }
}
