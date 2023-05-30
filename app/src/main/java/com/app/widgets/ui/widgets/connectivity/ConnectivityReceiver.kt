package com.app.widgets.ui.widgets.connectivity

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import com.app.widgets.model.ConnectivityInfo
import com.app.widgets.utils.isAirplaneEnabled
import com.app.widgets.utils.isBluetoothEnabled
import com.app.widgets.utils.isWifiEnabled
import com.app.widgets.utils.logd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ConnectivityReceiver : GlanceAppWidgetReceiver() {

    private val coroutineScope = MainScope()

    override val glanceAppWidget: GlanceAppWidget
        get() = ConnectivityWidget

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        observe(context = context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        val action = intent.action ?: return
        if (action !in actionList) return

        val flashLightToggled = if (action == ACTION_TOGGLE_FLASH_LIGHT) {
            intent.getBooleanExtra(FLASH_LIGHT_KEY, false)
        } else {
            false
        }

        observe(
            context = context,
            flashLightToggled = flashLightToggled,
        )


    }

    private fun observe(
        context: Context,
        flashLightToggled: Boolean? = null,
    ) {
        coroutineScope.launch(Dispatchers.IO) {
            val glanceId = GlanceAppWidgetManager(context)
                .getGlanceIds(ConnectivityWidget::class.java)
                .firstOrNull()
                ?: return@launch

            updateAppWidgetState(
                context = context,
                definition = ConnectivityStateDefinition,
                glanceId = glanceId
            ) {
                val isWifiEnabled = context.isWifiEnabled()
                val isBluetoothEnabled = context.isBluetoothEnabled()
                val isAirplaneEnabled = context.isAirplaneEnabled()

                ConnectivityInfo(
                    isWifiEnabled = isWifiEnabled,
                    isBluetoothEnabled = isBluetoothEnabled,
                    isAirplaneEnabled = isAirplaneEnabled,
                    isFlashLightOn = flashLightToggled ?: it.isFlashLightOn,
                )
            }

            ConnectivityWidget.updateAll(context)
        }
    }

    companion object {
        const val ACTION_WIFI_ENABLE_STATUS = "ACTION_WIFI_ENABLE_STATUS"
        const val ACTION_BLUETOOTH_ENABLE_STATUS = "ACTION_BLUETOOTH_ENABLE_STATUS"
        const val ACTION_AIRPLANE_ENABLE_STATUS = "ACTION_AIRPLANE_ENABLE_STATUS"
        const val ACTION_TOGGLE_FLASH_LIGHT = "ACTION_TOGGLE_FLASH_LIGHT"
        const val FLASH_LIGHT_KEY = "FLASH_LIGHT_KEY"
        val actionList = listOf(
            ACTION_WIFI_ENABLE_STATUS,
            ACTION_BLUETOOTH_ENABLE_STATUS,
            ACTION_AIRPLANE_ENABLE_STATUS,
            ACTION_TOGGLE_FLASH_LIGHT
        )
    }
}