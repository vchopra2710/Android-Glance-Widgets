package com.app.widgets.ui.widgets.connectivity

import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import com.app.widgets.utils.changeWifiState
import com.app.widgets.utils.isWifiEnabled
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ConnectivityWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = ConnectivityWidget

    private val coroutineScope = MainScope()

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val action = intent.action ?: return
        if (action == APPWIDGET_UPDATE) return

        coroutineScope.launch(Dispatchers.IO) {
            val glanceId = GlanceAppWidgetManager(context)
                .getGlanceIds(ConnectivityWidget::class.java)
                .firstOrNull()
                ?: return@launch

            updateAppWidgetState(context, glanceId) { prefs ->
                val enabled = if (action == ACTION_CHANGE_WIFI_STATE) {
                    context.changeWifiState()
                } else {
                    delay(500)
                    context.isWifiEnabled()
                }

                prefs[ConnectivityWidget.wifiEnabled] = enabled
                glanceAppWidget.update(context, glanceId)
            }


        }


    }

    companion object {
        private const val APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS"
        const val ACTION_CHANGE_WIFI_STATE = "ACTION_CHANGE_WIFI_STATE"
        const val ACTION_UPDATE_WIFI_STATE = "ACTION_UPDATE_WIFI_STATE"
    }
}