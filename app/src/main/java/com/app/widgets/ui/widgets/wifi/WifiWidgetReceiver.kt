package com.app.widgets.ui.widgets.wifi

import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import com.app.widgets.model.WifiInfo
import com.app.widgets.utils.changeWifiState
import com.app.widgets.utils.isWifiEnabled
import com.app.widgets.utils.loge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class WifiWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = WifiWidget

    private val coroutineScope = MainScope()

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val action = intent.action ?: return
        if (action == APPWIDGET_UPDATE) return
        val extras = intent.extras

        coroutineScope.launch(Dispatchers.IO) {
            val glanceId = GlanceAppWidgetManager(context)
                .getGlanceIds(WifiWidget::class.java)
                .firstOrNull()
                ?: return@launch

            updateAppWidgetState(
                context = context,
                definition = WifiInfoStateDefinition,
                glanceId = glanceId,
            ) {
                when (action) {
                    ACTION_CHANGE_WIFI_STATE -> {
                        val enabled = context.changeWifiState()
                        WifiInfo(
                            isWifiEnabled = enabled,
                            ssid = if (enabled) it.ssid else "-",
                            bssid = if (enabled) it.bssid else "-",
                            frequency = if (enabled) it.frequency else 0,
                            rssi = if (enabled) it.rssi else 0,
                        )
                    }

                    ACTION_UPDATE_WIFI_STATE -> {
                        val enabled = context.isWifiEnabled()
                        WifiInfo(isWifiEnabled = enabled)
                    }

                    ACTION_WIFI_INFO -> {
                        extras?.let { bundle ->
                            val wifiInfoString = bundle.getString(KEY_WIFI_INFO)

                            val wifiInfo = if (wifiInfoString == null) {
                                WifiInfo()
                            } else {
                                try {
                                    Json.decodeFromString(wifiInfoString)
                                } catch (e: Exception) {
                                    loge("exception", e)
                                    WifiInfo()
                                }
                            }

                            wifiInfo

                        } ?: WifiInfo()
                    }

                    else -> WifiInfo()
                }
            }

            WifiWidget.updateAll(context)
        }
    }

    companion object {
        private const val APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE_OPTIONS"
        const val ACTION_CHANGE_WIFI_STATE = "ACTION_CHANGE_WIFI_STATE"
        const val ACTION_UPDATE_WIFI_STATE = "ACTION_UPDATE_WIFI_STATE"
        const val ACTION_WIFI_INFO = "ACTION_WIFI_INFO"
        const val KEY_WIFI_INFO = "KEY_WIFI_INFO"
    }
}