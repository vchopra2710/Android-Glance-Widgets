package com.app.widgets.ui.widgets.connectivity

import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import com.app.widgets.utils.logd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

enum class WifiState {
    ENABLING,
    ENABLED,
    DISABLED,
    DISABLING;

    companion object {
        fun isFromWifiState(state: String): Boolean {
            val states = WifiState.values().toList().map { it.name }
            return state in states
        }

        fun toWifiState(state: String): WifiState? {
            return WifiState
                .values()
                .toList()
                .firstOrNull {
                    it.name == state
                }
        }
    }


}

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
                prefs[ConnectivityWidget.wifiEnabled] = WifiState.isFromWifiState(action)
                        && WifiState.toWifiState(action) == WifiState.ENABLED
                glanceAppWidget.update(context, glanceId)
            }
        }

    }

    companion object {
        private const val APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS"
    }
}