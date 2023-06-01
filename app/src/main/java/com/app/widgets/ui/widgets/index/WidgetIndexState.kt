package com.app.widgets.ui.widgets.index

data class WidgetIndexState(
    val wifiToggled: Boolean = false,
    val bluetoothToggled: Boolean = false,
    val airplaneToggled: Boolean = false,
    val flashlightToggled: Boolean = false,

    val widgetIndexContent: List<WidgetIndexContent> = listOf(
        WidgetIndexContent.Connectivity,
        WidgetIndexContent.Bluetooth,
        WidgetIndexContent.Airplane,
        WidgetIndexContent.Flashlight,
    )
)