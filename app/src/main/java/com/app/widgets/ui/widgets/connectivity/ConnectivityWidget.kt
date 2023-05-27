package com.app.widgets.ui.widgets.connectivity

import android.content.Context
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
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.app.widgets.R

object ConnectivityWidget : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        provideContent { Content() }
    }

    @Composable
    private fun Content() = Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon()
        Spacer(modifier = GlanceModifier.defaultWeight())
        NetworkName()
    }

    @Composable
    private fun Icon() {
        Image(
            provider = ImageProvider(R.drawable.wifi_0_bar),
            contentDescription = null,
            modifier = GlanceModifier.size(40.dp),
            colorFilter = ColorFilter.tint(ColorProvider(Color.White))
        )
    }

    @Composable
    private fun NetworkName() = Text(
        text = "network-name",
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            color = ColorProvider(Color.White),
            fontSize = 16.sp,
        ),
        maxLines = 1
    )

}