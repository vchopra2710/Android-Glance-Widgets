package com.app.widgets.ui.widgets.connectivity

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxWidth

object ConnectivityWidget : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId,
    ) {
        provideContent { Content() }
    }

    @Composable
    private fun Content() = Column(
        modifier = GlanceModifier.fillMaxWidth()
    ) {
        // TODO: add content for connectivity widget
    }

}