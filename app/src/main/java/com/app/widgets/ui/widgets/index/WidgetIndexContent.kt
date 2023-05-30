package com.app.widgets.ui.widgets.index

import androidx.annotation.StringRes
import com.app.widgets.R
import com.app.widgets.ui.widgets.index.WidgetIndexEvent.EnableConnectivity

sealed class WidgetIndexContent(
    @StringRes val text: Int,
    val action: WidgetIndexEvent,
) {
    object Connectivity : WidgetIndexContent(
        text = R.string.widget_connectivity,
        action = EnableConnectivity,
    )
}