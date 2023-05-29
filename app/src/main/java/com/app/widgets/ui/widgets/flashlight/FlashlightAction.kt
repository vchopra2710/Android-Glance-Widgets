package com.app.widgets.ui.widgets.flashlight

import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.app.widgets.ui.widgets.flashlight.FlashlightWidget.getClickTypeActionParameterKey
import com.app.widgets.ui.widgets.flashlight.FlashlightWidgetReceiver.Companion.CHANGE_FLASH_LIGHT_STATE

class FlashlightAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val widgetIntent = Intent(context, FlashlightWidgetReceiver::class.java).apply {
            this.action = CHANGE_FLASH_LIGHT_STATE
        }

        val toggle = parameters[getClickTypeActionParameterKey()]
        widgetIntent.putExtra(FLASH_LIGHT_KEY, toggle)

        context.sendBroadcast(widgetIntent)
    }

    companion object{
        const val FLASH_LIGHT_KEY = "flash_light_key"
    }
}