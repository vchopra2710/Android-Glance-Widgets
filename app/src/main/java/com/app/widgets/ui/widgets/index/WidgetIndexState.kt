package com.app.widgets.ui.widgets.index

data class WidgetIndexState(
    val connectivityEnabled: Boolean = false,
    val widgetIndexContent: List<WidgetIndexContent> = listOf(
        WidgetIndexContent.Connectivity
    )
)