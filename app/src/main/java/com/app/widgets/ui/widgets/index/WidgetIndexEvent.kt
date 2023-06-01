package com.app.widgets.ui.widgets.index

sealed class WidgetIndexEvent {
    object ToggleWifi : WidgetIndexEvent()
    object ToggleBluetooth : WidgetIndexEvent()
    object ToggleAirplane : WidgetIndexEvent()
    object ToggleFlashlight : WidgetIndexEvent()
}
