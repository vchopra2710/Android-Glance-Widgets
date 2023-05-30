package com.app.widgets.ui.widgets.wifi

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.Switch
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.app.widgets.R
import com.app.widgets.model.WifiInfo
import com.app.widgets.ui.widgets.actions.ChangeWifiStateAction
import com.app.widgets.utils.toWifiSignalIcon

object WifiWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*>
        get() = WifiInfoStateDefinition

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        provideContent {
            val wifiInfo = currentState<WifiInfo>()
            Content(wifiInfo = wifiInfo)
        }
    }

    @Composable
    private fun Content(
        wifiInfo: WifiInfo,
    ) = Row(
        modifier = GlanceModifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color.Black.copy(alpha = 0.2f))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val isWifiEnabled = wifiInfo.isWifiEnabled
        Column(
            modifier = GlanceModifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                resId = if (isWifiEnabled && wifiInfo.rssi >= 0) R.drawable.wifi_find
                else if (wifiInfo.rssi < 0) wifiInfo.rssi.toWifiSignalIcon()
                else R.drawable.wifi_off,
                tint = if(wifiInfo.rssi < 0) Color.Cyan.copy(alpha = 0.5f) else Color.White
            )
            Spacer(modifier = GlanceModifier.defaultWeight())
            Enabled(text = if (isWifiEnabled) "Enabled" else "Disabled")
        }

        Spacer(modifier = GlanceModifier.width(8.dp))


        Column(
            modifier = GlanceModifier.fillMaxHeight().defaultWeight(),
            horizontalAlignment = Alignment.Start,
            verticalAlignment = Alignment.Top,
        ) {
            Data(text = "ssid: ${if (isWifiEnabled) wifiInfo.ssid else "-"}")
            Data(text = "bssid: ${if (isWifiEnabled) wifiInfo.bssid else "-"}")
            Data(text = "frequency: ${if (isWifiEnabled) wifiInfo.frequency else "-"}")
            Data(text = "rssi: ${if (isWifiEnabled) wifiInfo.rssi else "-"}")
        }

        Spacer(modifier = GlanceModifier.width(8.dp))

        Switch(
            checked = isWifiEnabled,
            onCheckedChange = actionRunCallback(ChangeWifiStateAction::class.java),
        )
    }

    @Composable
    private fun Icon(
        @DrawableRes resId: Int,
        tint: Color= Color.White
    ) = Image(
        provider = ImageProvider(resId),
        contentDescription = null,
        modifier = GlanceModifier.size(40.dp),
        colorFilter = ColorFilter.tint(ColorProvider(tint))
    )

    @Composable
    private fun Enabled(text: String) = Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            color = ColorProvider(Color.White),
            fontSize = 16.sp,
        ),
        maxLines = 1
    )

    @Composable
    private fun Data(text: String) = Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            color = ColorProvider(Color.White),
            fontSize = 12.sp,
        ),
        maxLines = 1
    )

}