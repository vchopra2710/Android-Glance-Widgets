package com.app.widgets.ui.widgets.connectivity

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.unit.ColorProvider
import com.app.widgets.model.ConnectivityInfo
import com.app.widgets.model.ConnectivityType
import com.app.widgets.model.ConnectivityType.Companion.actionParameters
import com.app.widgets.model.ConnectivityType.Companion.callbackClass
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver.Companion.FLASH_LIGHT_KEY

object ConnectivityWidget : GlanceAppWidget() {
    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        provideContent {
            val connectivityInfo = currentState<ConnectivityInfo>()
            Content(connectivityInfo = connectivityInfo)
        }
    }

    @Composable
    private fun Content(
        connectivityInfo: ConnectivityInfo,
    ) = Row(
        modifier = GlanceModifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val connectivityList = ConnectivityType.values().toList()

        connectivityList.forEach { connectivityType ->
            ConnectivityIcon(
                enabled = when (connectivityType.name) {
                    ConnectivityType.WIFI.name -> connectivityInfo.isWifiEnabled
                    ConnectivityType.BLUETOOTH.name -> connectivityInfo.isBluetoothEnabled
                    ConnectivityType.AIRPLANE.name -> connectivityInfo.isAirplaneEnabled
                    ConnectivityType.FLASH_LIGHT.name -> connectivityInfo.isFlashLightOn
                    else -> false
                },
                connectivityType = connectivityType,
            )
            if (connectivityList.indexOf(connectivityType) != connectivityList.size - 1) {
                Spacer(modifier = GlanceModifier.width(8.dp))
            }
        }
    }

    @Composable
    private fun ConnectivityIcon(
        enabled: Boolean,
        connectivityType: ConnectivityType,
    ) = Box(
        modifier = GlanceModifier
            .size(60.dp)
            .cornerRadius(30.dp)
            .background(
                color = if (enabled) connectivityType.enabledBgColor
                else connectivityType.disabledBgColor
            )
            .clickable(
                actionRunCallback(
                    callbackClass = connectivityType.callbackClass(),
                    parameters = connectivityType.actionParameters(
                        boolean = !enabled
                    )
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            provider = ImageProvider(
                resId = if (enabled) connectivityType.enabledIcon
                else connectivityType.disabledIcon
            ),
            contentDescription = connectivityType.name,
            colorFilter = ColorFilter.tint(
                ColorProvider(
                    color = if (enabled) connectivityType.enabledIconTint
                    else connectivityType.disabledIconTint
                )
            ),
            modifier = GlanceModifier.size(36.dp)
        )
    }
}

fun getClickTypeActionParameterKey(): ActionParameters.Key<Int> {
    return ActionParameters.Key(FLASH_LIGHT_KEY)
}