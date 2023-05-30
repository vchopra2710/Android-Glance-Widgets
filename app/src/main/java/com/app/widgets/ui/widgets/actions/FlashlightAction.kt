package com.app.widgets.ui.widgets.actions

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver.Companion.ACTION_TOGGLE_FLASH_LIGHT
import com.app.widgets.ui.widgets.connectivity.ConnectivityReceiver.Companion.FLASH_LIGHT_KEY
import com.app.widgets.ui.widgets.connectivity.getClickTypeActionParameterKey
import com.app.widgets.utils.toggleFlashLight

class FlashlightAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val parameter = parameters[getClickTypeActionParameterKey()]
        val toggle = context.toggleFlashLight(parameter == 0)

        val widgetIntent = Intent(context, ConnectivityReceiver::class.java).apply {
            this.action = ACTION_TOGGLE_FLASH_LIGHT
            putExtra(FLASH_LIGHT_KEY, toggle)
        }

        context.sendBroadcast(widgetIntent)
    }
}