package com.app.widgets.ui.widgets.connectivity

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.booleanPreferencesKey
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
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.wrapContentWidth
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.app.widgets.R

object ConnectivityWidget : GlanceAppWidget() {

    val wifiEnabled = booleanPreferencesKey("wifiEnabled")

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        provideContent {
            val isWifiEnabled = currentState(key = wifiEnabled)
            Content(
                wifiEnabled = isWifiEnabled ?: false,
            )
        }
    }

    @Composable
    private fun Content(
        wifiEnabled: Boolean,
    ) = Row(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = GlanceModifier
                .wrapContentWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(resId = if (wifiEnabled) R.drawable.wifi_on else R.drawable.wifi_off)
            Spacer(modifier = GlanceModifier.defaultWeight())
            NetworkName(text = if (wifiEnabled) "Enabled" else "Disabled")
        }

        Spacer(modifier = GlanceModifier.defaultWeight())

        Switch(
            checked = wifiEnabled,
            onCheckedChange = actionRunCallback(ChangeWifiStateAction::class.java),
        )
    }

    @Composable
    private fun Icon(@DrawableRes resId: Int) {
        Image(
            provider = ImageProvider(resId),
            contentDescription = null,
            modifier = GlanceModifier.size(40.dp),
            colorFilter = ColorFilter.tint(ColorProvider(Color.White))
        )
    }

    @Composable
    private fun NetworkName(text: String) = Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            color = ColorProvider(Color.White),
            fontSize = 16.sp,
        ),
        maxLines = 1
    )

}