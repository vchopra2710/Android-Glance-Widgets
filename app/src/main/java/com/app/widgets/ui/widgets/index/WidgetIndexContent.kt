package com.app.widgets.ui.widgets.index

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.app.widgets.R
import com.app.widgets.ui.widgets.index.WidgetIndexEvent.ToggleAirplane
import com.app.widgets.ui.widgets.index.WidgetIndexEvent.ToggleBluetooth
import com.app.widgets.ui.widgets.index.WidgetIndexEvent.ToggleFlashlight
import com.app.widgets.ui.widgets.index.WidgetIndexEvent.ToggleWifi

sealed class WidgetIndexContent(
    @DrawableRes val enableIcon: Int,
    @DrawableRes val disableIcon: Int,
    @StringRes val text: Int,
    val action: WidgetIndexEvent,
) {
    object Connectivity : WidgetIndexContent(
        enableIcon = R.drawable.signal_wifi_4_bar,
        disableIcon = R.drawable.wifi_off,
        text = R.string.widget_wifi,
        action = ToggleWifi,
    )

    object Bluetooth : WidgetIndexContent(
        enableIcon = R.drawable.bluetooth_on,
        disableIcon = R.drawable.bluetooth_off,
        text = R.string.widget_bluetooth,
        action = ToggleBluetooth,
    )

    object Airplane : WidgetIndexContent(
        enableIcon = R.drawable.airplanemode_active,
        disableIcon = R.drawable.airplanemode_inactive,
        text = R.string.widget_airplane,
        action = ToggleAirplane,
    )

    object Flashlight : WidgetIndexContent(
        enableIcon = R.drawable.flashlight_on,
        disableIcon = R.drawable.flashlight_off,
        text = R.string.widget_flash_light,
        action = ToggleFlashlight,
    )
}